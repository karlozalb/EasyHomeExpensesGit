package com.projectclean.easyhomeexpenses.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.projectclean.easyhomeexpenses.R
import com.projectclean.easyhomeexpenses.activities.NewExpenseActivity
import com.projectclean.easyhomeexpenses.adapters.ExpenseListsAdapter
import com.projectclean.easyhomeexpenses.adapters.ExpensesAdapter
import com.projectclean.easyhomeexpenses.database.ExpenseDatabaseRequester
import com.projectclean.easyhomeexpenses.database.ExpensesDataBase
import com.projectclean.easyhomeexpenses.models.Expense
import com.projectclean.easyhomeexpenses.models.ExpenseList
import kotlinx.android.synthetic.main.offline_list_fragment.*

class OfflineListsFragment : ExpensesListFragment() {

    private var mainAdapter : ExpenseListsAdapter? = null
    private var databaseRequester : ExpenseDatabaseRequester? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.offline_list_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainAdapter = ExpenseListsAdapter(this)

        offline_lists_recycler_view.layoutManager = LinearLayoutManager(context)
        offline_lists_recycler_view.adapter = mainAdapter

        databaseRequester = ExpenseDatabaseRequester(ExpensesDataBase.instance!!.expenseDao())

        updateAdapter()

        add_offline_expense_btn.setOnClickListener { view ->
           var intent = Intent(context, NewExpenseActivity::class.java)
           startActivity(intent)
       }
    }

    private fun updateAdapter()
    {
        /*databaseRequester!!.GetAllExpenses(
                {
                    elements ->
                    run{
                        var list = mutableListOf<ExpenseList>()
                        val iterator = elements.iterator()

                        iterator.forEach{
                            list.add(Expense(it))
                        }

                        mainAdapter!!.setElements(list)
                    }
                })*/
    }

    override fun onResume() {
        super.onResume()
        updateAdapter()
    }

    override fun onItemClicked(list: ExpenseList) {

    }
}
