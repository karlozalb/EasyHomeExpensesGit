package com.projectclean.easyhomeexpenses.database

import android.graphics.Bitmap
import android.os.AsyncTask
import com.projectclean.easyhomeexpenses.models.Expense


/**
 * Created by Carlos Albaladejo Pérez on 21/03/2018.
 */

class ExpenseDatabaseRequester(var databaseDao : ExpenseEntityDao)
{

    fun InsertExpense(expense : ExpenseEntity , callback : () -> Unit)
    {
        InsertExpenseAsync(callback).execute(expense)
    }

    fun GetAllExpenses(callback : (List<ExpenseEntity>) -> Unit)
    {
        GetExpensesAsync(callback).execute()
    }

    private inner class GetExpensesAsync(var callback : (List<ExpenseEntity>) -> Unit) : AsyncTask<Long, Int, List<ExpenseEntity>>() {

        override fun doInBackground(vararg ids : Long?): List<ExpenseEntity>? {
            var result  = mutableListOf<ExpenseEntity>()
            if (ids.isEmpty()) //We want all of them!
            {
                return databaseDao.getAllExpenses()
            }
            else //We only want one of them.
            {
                result.add(databaseDao.findExpenseById(ids[0]!!))
                return result
            }
        }

        override fun onPostExecute(result: List<ExpenseEntity>)
        {
            callback!!(result)
        }
    }

    private inner class InsertExpenseAsync(var callback : () -> Unit) : AsyncTask<ExpenseEntity, Int, Unit>() {

        override fun doInBackground(vararg expenses : ExpenseEntity?)
        {
            databaseDao.insertExpense(expenses[0]!!)
        }

        override fun onPostExecute(result: Unit)
        {
            callback!!()
        }
    }

}