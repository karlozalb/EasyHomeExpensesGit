package com.projectclean.easyhomeexpenses.activities

import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.projectclean.easyhomeexpenses.R
import com.projectclean.easyhomeexpenses.adapters.ExpensesAdapter
import com.projectclean.easyhomeexpenses.database.FirebaseController
import com.projectclean.easyhomeexpenses.models.Expense
import kotlinx.android.synthetic.main.activity_expenses.*

class ExpensesActivity : Activity() {

    companion object {
        val ONLINE_MODE : String = "ONLINE_MODE"
        val LIST_ID : String = "LIST_ID"
        val TAG = "ExpensesActivity"
    }

    private var mOnlineMode : Boolean = false
    private var mListId : String = ""

    private lateinit var mMainAdapter : ExpensesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenses)

        add_expense_btn.setOnClickListener(
                {
                    addExpense()
                }
        );

        setAdapter()

        mOnlineMode = intent.getBooleanExtra(ONLINE_MODE, false)

        if (mOnlineMode)
        {
            //We feed the adapter with Firebase data.
            mListId = intent.getStringExtra(LIST_ID)
        }
        else
        {
            //We feed the adapter with Room data.
        }

        updateAdapter()
    }

    private fun setAdapter()
    {
        mMainAdapter = ExpensesAdapter()
        expenses_recycler_view.layoutManager = LinearLayoutManager(this)
        expenses_recycler_view.adapter = mMainAdapter
    }

    private fun updateAdapter()
    {
        if (mOnlineMode)
        {
            FirebaseController.instance?.getExpenses(mListId,{ list -> mMainAdapter.setElements(list)})
        }
        else
        {

        }
    }

    private fun addExpense()
    {
        var intent = Intent(this, OnlineNewExpenseActivity::class.java)
        intent.putExtra(OnlineNewExpenseActivity.LIST_ID,mListId);
        startActivity(intent)
    }

    fun onItemClicked(expense: Expense)
    {
        Log.e(TAG, "list:"+expense.id)

        var intent = Intent(this, ExpensesActivity::class.java)
        intent.putExtra(ExpensesActivity.ONLINE_MODE, true)
        intent.putExtra(ExpensesActivity.LIST_ID, list.listId)
        startActivity(intent)
    }

}
