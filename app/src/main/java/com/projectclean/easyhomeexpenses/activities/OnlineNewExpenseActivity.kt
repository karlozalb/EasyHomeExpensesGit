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

class OnlineNewExpenseActivity : NewExpenseActivity() {

    companion object {
        var LIST_ID = "LIST_ID"
        var TAG = "OnlineNewExpenseActivity"
    }

    private lateinit var mListId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mListId = intent.getStringExtra(LIST_ID)
    }

   override fun SaveExpense(exp : ExpenseEntity)
   {
       FirebaseController.instance?.createExpense(mListId,exp,
               { Log.d(TAG, "Expense added successfully.")},
               { Log.e(TAG, "Error adding expense.")}
       )
   }

}