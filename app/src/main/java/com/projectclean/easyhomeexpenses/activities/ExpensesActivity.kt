package com.projectclean.easyhomeexpenses.activities

import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
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

        //registerForContextMenu(expenses_recycler_view)

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
    }

    override fun onPause() {
        super.onPause()
        unregisterForContextMenu(expenses_recycler_view)
    }

    override fun onResume() {
        super.onResume()
        registerForContextMenu(expenses_recycler_view)
        updateAdapter()
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId){
            R.id.delete_expense -> {
                if (mOnlineMode) {
                    FirebaseController.instance?.deleteExpense(mListId, mMainAdapter.getLongSelectedItem().id,
                            {
                                Log.d(TAG, "Expense deleted correctly.")
                                updateAdapter()
                            },
                            {
                                Log.d(TAG, "Expense deletion FAILED.")
                            }
                    )
                }
                else
                {

                }
            }
        }

        return super.onContextItemSelected(item)
    }

    private fun setAdapter()
    {
        mMainAdapter = ExpensesAdapter(this)
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
        if (mOnlineMode) {
            var intent = Intent(this, OnlineNewExpenseActivity::class.java)
            intent.putExtra(OnlineNewExpenseActivity.LIST_ID, mListId);
            startActivity(intent)
        }
        else
        {

        }
    }

    fun onItemClicked(expense: Expense)
    {
        Log.e(TAG, "list:"+expense.id)
        if (mOnlineMode)
        {
            var intent = Intent(this, OnlineEditExpenseActivity::class.java)
            intent.putExtra(OnlineEditExpenseActivity.LIST_ID, mListId)
            intent.putExtra(OnlineEditExpenseActivity.EXPENSE_ID, expense.id)

            intent.putExtra(EditExpenseActivity.NAME, expense.name)
            intent.putExtra(EditExpenseActivity.DATE, expense.date.toString())
            intent.putExtra(EditExpenseActivity.MONEY, expense.money.toString())

            startActivity(intent)
        }
        else
        {

        }
    }

}
