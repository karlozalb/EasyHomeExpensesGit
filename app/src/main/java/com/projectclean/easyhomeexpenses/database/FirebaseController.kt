package com.projectclean.easyhomeexpenses.database

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import com.projectclean.easyhomeexpenses.models.Expense
import com.projectclean.easyhomeexpenses.models.ExpenseList
import java.lang.Float.parseFloat
import java.lang.Long.parseLong
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

/**
 * Created by Carlos Albaladejo Pérez on 13/04/2018.
 */

class FirebaseController(var db : FirebaseFirestore, var user : FirebaseUser)
{

    companion object {
        var instance : FirebaseController? =  null

        fun init(db : FirebaseFirestore, user : FirebaseUser)
        {
            instance = FirebaseController(db,user)
        }
    }

    val EXPENSES_COLLECTION : String = "ExpensesCollection"
    val LISTS_COLLECTION : String = "ListsCollection"

    fun getUserId() : String
    {
        return user.uid
    }

    fun createExpense(listId : String, expense : ExpenseEntity, onSuccess : () -> Unit, onError : () -> Unit)
    {
        var newExpense = hashMapOf<String,Any>()

        newExpense["creator_id"] = user.uid
        newExpense["name"] = expense.name
        newExpense["date"] = expense.date
        newExpense["money"] = expense.money

        db.collection(LISTS_COLLECTION)
                .document(listId)
                .collection(EXPENSES_COLLECTION)
                .add(expense)
                .addOnCompleteListener { onSuccess() }
                .addOnFailureListener { onError }
    }

    fun updateExpense(listId : String, expenseId : String, expense : ExpenseEntity, onSuccess : () -> Unit, onError : () -> Unit)
    {
        var updatedExpense = hashMapOf<String,Any>()

        updatedExpense["creator_id"] = user.uid
        updatedExpense["name"] = expense.name
        updatedExpense["date"] = expense.date
        updatedExpense["money"] = expense.money

        db.collection(LISTS_COLLECTION)
                .document(listId)
                .collection(EXPENSES_COLLECTION)
                .document(expenseId)
                .set(updatedExpense, SetOptions.merge())
                .addOnCompleteListener { onSuccess() }
                .addOnFailureListener { onError }
    }

    fun deleteExpense(listId :  String, expenseId : String, onSuccess : () -> Unit, onError : () -> Unit)
    {
        db.collection(LISTS_COLLECTION)
                .document(listId)
                .collection(EXPENSES_COLLECTION)
                .document(expenseId)
                .delete()
                .addOnCompleteListener { onSuccess() }
                .addOnFailureListener { onError }
    }

    fun getExpenses(listId: String, onSuccess : (List<Expense>) -> Unit )
    {
        db.collection(LISTS_COLLECTION).document(listId).collection(EXPENSES_COLLECTION).get().addOnCompleteListener { task -> run{
            if (task.isSuccessful)
            {
                var list : List<DocumentSnapshot> = task.result.documents

                var expenses = mutableListOf<Expense>()

                list.forEach { document -> run {
                    expenses.add(Expense(document.data["name"].toString(), Date(document.data["date"].toString()),parseFloat(document.data["money"].toString()),document.data["ownerId"].toString(),document.id))
                } }

                onSuccess(expenses)
            }
        } }
    }

    fun createNewList(name : String, periodStart : Int, onSuccess : (String) -> Unit, onError : () -> Unit)
    {
        var newList = hashMapOf<String,Any>()

        var sharedWith = hashMapOf<String,Any>()

        newList["creator_id"] = user.uid
        newList["shared_with"] = sharedWith
        newList["name"] = name
        newList["periodStart"] = periodStart

        db.collection(LISTS_COLLECTION)
                .add(newList)
                .addOnCompleteListener {
                    task -> run{
                        if (task.isSuccessful)
                        {
                            var list : DocumentReference = task.result
                            onSuccess(list.id)
                        }
                    }
                }
                .addOnFailureListener { onError }
    }

    fun getLists(onSuccess : (List<ExpenseList>) -> Unit )
    {
        db.collection(LISTS_COLLECTION).get().addOnCompleteListener { task -> run{
            if (task.isSuccessful)
            {
                var list : List<DocumentSnapshot> = task.result.documents

                var expensesList = mutableListOf<ExpenseList>()

                list.forEach { document -> run {
                    expensesList.add(ExpenseList(document.data["name"].toString(),document.data["ownerName"].toString(), document.data["sharedWith"].toString(),document.id))
                } }

                onSuccess(expensesList)
            }
        } }
    }

    fun shareListWithOther(listId : String , userid : String)
    {
        var list = hashMapOf<String,Any>()
        var sharedWith = hashMapOf<String,Any>()

        sharedWith[userid] = true
        list["shared_with"] = sharedWith

        db.collection(LISTS_COLLECTION).document(listId).set(list, SetOptions.merge())
    }

}