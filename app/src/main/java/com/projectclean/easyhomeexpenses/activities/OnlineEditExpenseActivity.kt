package com.projectclean.easyhomeexpenses.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.projectclean.easyhomeexpenses.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.view.View.OnFocusChangeListener
import com.projectclean.easyhomeexpenses.database.ExpenseDatabaseRequester
import com.projectclean.easyhomeexpenses.database.ExpenseEntity
import com.projectclean.easyhomeexpenses.database.ExpensesDataBase
import com.projectclean.easyhomeexpenses.database.FirebaseController
import kotlinx.android.synthetic.main.new_expense_view.*
import com.projectclean.easyhomeexpenses.extensions.fromDateToEditable
import com.projectclean.easyhomeexpenses.extensions.fromMillisToEditable
import com.projectclean.easyhomeexpenses.extensions.snack
import com.projectclean.easyhomeexpenses.models.Expense
import java.text.SimpleDateFormat


/**
 * Created by Carlos Albaladejo Pérez on 01/03/2018.
 */

class OnlineEditExpenseActivity : EditExpenseActivity() {

    companion object {
        var LIST_ID = "LIST_ID"
        var EXPENSE_ID = "EXPENSE_ID"
        var TAG = "OnlineEditExpenseActivity"
    }

    private lateinit var mListId : String
    private lateinit var mExpenseId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mListId = intent.getStringExtra(LIST_ID)
        mExpenseId = intent.getStringExtra(EXPENSE_ID)
    }

    override fun UpdateExpense(exp: ExpenseEntity) {
        FirebaseController.instance?.updateExpense(mListId,mExpenseId,exp,
                {Log.d(OnlineEditExpenseActivity.TAG, "Expense updated successfully.")},
                {Log.e(OnlineEditExpenseActivity.TAG, "Error updating expense.")}
        )
    }
}