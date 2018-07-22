package com.projectclean.easyhomeexpenses.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.projectclean.easyhomeexpenses.R
import com.projectclean.easyhomeexpenses.activities.ExpensesActivity
import com.projectclean.easyhomeexpenses.activities.NewExpenseActivity
import com.projectclean.easyhomeexpenses.adapters.ExpenseListsAdapter
import com.projectclean.easyhomeexpenses.adapters.ExpensesAdapter
import com.projectclean.easyhomeexpenses.database.ExpenseDatabaseRequester
import com.projectclean.easyhomeexpenses.database.ExpensesDataBase
import com.projectclean.easyhomeexpenses.models.Expense
import com.projectclean.easyhomeexpenses.models.ExpenseList
import kotlinx.android.synthetic.main.offline_list_fragment.*

abstract class ExpensesListFragment : Fragment() {

    abstract fun onItemClicked(list: ExpenseList)

}
