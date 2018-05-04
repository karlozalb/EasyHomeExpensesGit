package com.projectclean.easyhomeexpenses.database

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import com.projectclean.easyhomeexpenses.models.Expense
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

    fun createExpense(listId : String, expense : Expense, onSuccess : () -> Unit, onError : () -> Unit)
    {
        var newExpense = hashMapOf<String,Any>()

        newExpense["creator_id"] = user.uid
        newExpense["list_id"] = listId
        newExpense["name"] = expense.name
        newExpense["date"] = expense.date
        newExpense["money"] = expense.money
        newExpense["ownerName"] = expense.ownerName

        db.collection(LISTS_COLLECTION)
                .document(listId)
                .collection(EXPENSES_COLLECTION)
                .add(expense)
                .addOnCompleteListener { onSuccess() }
                .addOnFailureListener { onError }
    }

    fun updateExpense(listId : String, expenseId : String, expense : Expense, onSuccess : () -> Unit, onError : () -> Unit)
    {
        var updatedExpense = hashMapOf<String,Any>()

        updatedExpense["creator_id"] = user.uid
        updatedExpense["name"] = expense.name
        updatedExpense["date"] = expense.date
        updatedExpense["money"] = expense.money
        updatedExpense["ownerName"] = expense.ownerName

        db.collection(LISTS_COLLECTION)
                .document(listId)
                .collection(EXPENSES_COLLECTION)
                .document(expenseId)
                .set(updatedExpense, SetOptions.merge())
                .addOnCompleteListener { onSuccess() }
                .addOnFailureListener { onError }
    }

    fun createNewList(onSuccess : (String) -> Unit, onError : () -> Unit)
    {
        var newList = hashMapOf<String,Any>()

        var sharedWith = hashMapOf<String,Any>()

        newList["creator_id"] = user.uid
        newList["shared_with"] = sharedWith

        db.collection(LISTS_COLLECTION)
                .add(newList)
                .addOnCompleteListener {
                    task -> run{
                        if (task.isSuccessful)
                        {
                            var list : DocumentReference = task.result;
                            onSuccess(list.id)
                        }
                    }
                }
                .addOnFailureListener { onError }
    }

    fun getList()
    {
        db.collection(LISTS_COLLECTION).get().addOnCompleteListener { task -> run{
            if (task.isSuccessful)
            {
                var list : List<DocumentSnapshot> = task.result.documents

                list.forEach { document -> run {
                    println(document.data["creator_id"])
                    println(document.data["shared_with"])
                } }
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