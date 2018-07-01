package com.projectclean.easyhomeexpenses.activities

import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.projectclean.easyhomeexpenses.R
import com.projectclean.easyhomeexpenses.adapters.ExpensesAdapter
import com.projectclean.easyhomeexpenses.database.FirebaseController
import kotlinx.android.synthetic.main.activity_expenses.*
import kotlinx.android.synthetic.main.offline_list_fragment.*

class ExpensesActivity : Activity() {

    companion object {
        val ONLINE_MODE : String = "ONLINE_MODE"
        val LIST_ID : String = "LIST_ID"
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
        var intent = Intent(this, NewExpenseActivity::class.java)
        startActivity(intent)
    }

}
