package com.projectclean.easyhomeexpenses.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.projectclean.easyhomeexpenses.R
import com.projectclean.easyhomeexpenses.database.ExpenseEntity
import com.projectclean.easyhomeexpenses.extensions.snack
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.new_expense_view.*
import java.text.SimpleDateFormat
import java.util.*

abstract class EditExpenseActivity : AppCompatActivity() {

    companion object {
        val OWNER_NAME : String = "OwnerName"
        val NAME : String = "Name"
        val MONEY = "Money"
        val DATE = "Date"

        val TAG = "EditExpenseActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_expense_view)
        setSupportActionBar(toolbar)

        var cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)

            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }

        newExpenseCancelButton.setOnClickListener {
            finish()
        }

        newExpenseOkButton.setOnClickListener {
            var exp = ExpenseEntity()

            val formatter = SimpleDateFormat(NewExpenseActivity.DateFormat)
            val date = formatter.parse(newExpenseDate.text.toString())

            exp.date        = date

            try {
                exp.money       = newExpenseCost.text.toString()
                exp.name        = newExpenseName.text.toString()
                exp.ownerName   = newExpenseOwner.text.toString()

                UpdateExpense(exp)

                finish()
            }   catch (ex : NumberFormatException){
                root_view.snack("Test", Snackbar.LENGTH_LONG)
            }
        }

        newExpenseDate.setOnClickListener {
            DatePickerDialog(this, dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        newExpenseDate.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                DatePickerDialog(this, dateSetListener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        }

        Log.d(TAG,"OWNER_NAME:" + intent.getStringExtra(OWNER_NAME) )
        Log.d(TAG,"NAME:" + intent.getStringExtra(NAME) )
        Log.d(TAG,"MONEY:" + intent.getStringExtra(MONEY) )
        Log.d(TAG,"DATE:" + intent.getStringExtra(DATE) )

        newExpenseOwner.setText(intent.getStringExtra(OWNER_NAME))
        newExpenseName.setText(intent.getStringExtra(NAME))
        newExpenseCost.setText(intent.getStringExtra(MONEY))
        newExpenseDate.setText(intent.getStringExtra(DATE))
    }

    abstract fun UpdateExpense(exp : ExpenseEntity)
}