package com.projectclean.easyhomeexpenses.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.projectclean.easyhomeexpenses.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.new_expense_view.*

class EditExpenseActivity : AppCompatActivity() {

    companion object {
        val OWNER_NAME : String = "OwnerName"
        val NAME : String = "Name"
        val MONEY = "Money"
        val DATE = "Date"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_expense_view)
        setSupportActionBar(toolbar)

        newExpenseOwner.setText(intent.getStringExtra(OWNER_NAME))
        newExpenseOwner.setText(intent.getStringExtra(NAME))
        newExpenseOwner.setText(intent.getStringExtra(MONEY))
        newExpenseOwner.setText(intent.getStringExtra(DATE))

    }
}