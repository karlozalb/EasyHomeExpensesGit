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
import com.projectclean.easyhomeexpenses.adapters.ExpensesAdapter
import com.projectclean.easyhomeexpenses.database.ExpenseDatabaseRequester
import com.projectclean.easyhomeexpenses.database.ExpensesDataBase
import com.projectclean.easyhomeexpenses.models.Expense
import kotlinx.android.synthetic.main.offline_list_fragment.*

class OfflineExpensesFragment : Fragment() {

    private var mainAdapter : ExpensesAdapter? = null
    private var databaseRequester : ExpenseDatabaseRequester? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view = inflater!!.inflate(R.layout.offline_list_fragment, container, false)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainAdapter = ExpensesAdapter()

        expenses_recycler_view.layoutManager = LinearLayoutManager(context)
        expenses_recycler_view.adapter = mainAdapter

        databaseRequester = ExpenseDatabaseRequester(ExpensesDataBase.instance!!.expenseDao())

        updateAdapter()

        add_offline_expense_btn.setOnClickListener { view ->
           var intent = Intent(context, NewExpenseActivity::class.java)
           startActivity(intent)
       }
    }

    private fun updateAdapter()
    {
        databaseRequester!!.GetAllExpenses(
                {
                    elements ->
                    run{
                        var list = mutableListOf<Expense>()
                        val iterator = elements.iterator()

                        iterator.forEach{
                            list.add(Expense(it))
                        }

                        mainAdapter!!.setElements(list)
                    }
                })
    }

    override fun onResume() {
        super.onResume()
        updateAdapter()
    }
}