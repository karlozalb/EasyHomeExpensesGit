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
import kotlinx.android.synthetic.main.new_expense_view.*
import com.projectclean.easyhomeexpenses.extensions.fromDateToEditable
import com.projectclean.easyhomeexpenses.extensions.fromMillisToEditable
import com.projectclean.easyhomeexpenses.extensions.snack
import java.text.SimpleDateFormat


/**
 * Created by Carlos Albaladejo Pérez on 01/03/2018.
 */

class NewExpenseActivity : AppCompatActivity() {

    companion object {
        const val DateFormat =  "dd.MM.yyyy"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_expense_view)
        setSupportActionBar(toolbar)

        var cal = Calendar.getInstance()

        newExpenseDate.text = newExpenseDate.fromMillisToEditable(System.currentTimeMillis())

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)

            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            newExpenseDate.text = newExpenseDate.fromDateToEditable(cal.time)
        }

        newExpenseCancelButton.setOnClickListener {
            finish()
        }

        newExpenseOkButton.setOnClickListener {
            var exp = ExpenseEntity()

            val formatter = SimpleDateFormat(DateFormat)
            val date = formatter.parse(newExpenseDate.text.toString())

            exp.date        = date

            try {
                var number = newExpenseCost.text.toString().toFloat()

                exp.money       = newExpenseCost.text.toString()
                exp.name        = newExpenseName.text.toString()
                exp.ownerName   = newExpenseOwner.text.toString()

                var requester = ExpenseDatabaseRequester(ExpensesDataBase.instance!!.expenseDao())
                requester.InsertExpense(exp, { Log.i("NEWEXPENSE", "New expense added successfully.") })

                finish()
            }   catch (ex : NumberFormatException){
                root_view.snack("Test",Snackbar.LENGTH_LONG)
            }
        }

        newExpenseDate.setOnClickListener {
            DatePickerDialog(this, dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        newExpenseDate.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                DatePickerDialog(this, dateSetListener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        }
    }

}