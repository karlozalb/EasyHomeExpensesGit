package com.projectclean.easyhomeexpenses.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.projectclean.easyhomeexpenses.R
import com.projectclean.easyhomeexpenses.databinding.ExpenseViewBinding
import com.projectclean.easyhomeexpenses.extensions.inflate
import com.projectclean.easyhomeexpenses.models.Expense


/**
 * Created by Carlos Albaladejo Pérez on 24/02/2018.
 */

class ExpensesAdapter(var items : List<Expense>, var listener : (Expense) -> Unit) : RecyclerView.Adapter<ExpensesAdapter.ViewHolder>()
{
    class ViewHolder(var expenseView: View) : RecyclerView.ViewHolder(expenseView)
    {
        fun bind(expense: Expense, listener:(Expense) -> Unit)
        {
            var binding = ExpenseViewBinding()
            binding.expense = expense
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.expense_view))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], listener)

    override fun getItemCount() = items.size

}