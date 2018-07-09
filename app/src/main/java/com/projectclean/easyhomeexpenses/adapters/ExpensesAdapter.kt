package com.projectclean.easyhomeexpenses.adapters

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.projectclean.easyhomeexpenses.BR
import com.projectclean.easyhomeexpenses.R
import com.projectclean.easyhomeexpenses.activities.ExpensesActivity
import com.projectclean.easyhomeexpenses.databinding.ExpenseViewBinding
import com.projectclean.easyhomeexpenses.models.Expense


/**
 * Created by Carlos Albaladejo Pérez on 24/02/2018.
 */

class ExpensesAdapter(var ownerActivity : ExpensesActivity) : RecyclerView.Adapter<ExpensesAdapter.ExpenseViewHolder>()
{
    private var items : List<Expense> = listOf()

    class ExpenseViewHolder(var binding: ExpenseViewBinding, var ownerActivity : ExpensesActivity) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(expense: Expense)
        {
            binding.setVariable(BR.expense, expense)
            binding.setVariable(BR.activity, ownerActivity)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ExpenseViewHolder
    {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ExpenseViewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.expense_view, parent, false)

        return ExpenseViewHolder(binding, ownerActivity)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    fun setElements(items : List<Expense>)
    {
        this.items = items
        notifyDataSetChanged()
    }

}