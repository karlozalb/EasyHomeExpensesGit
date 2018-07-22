package com.projectclean.easyhomeexpenses.adapters

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.projectclean.easyhomeexpenses.BR
import com.projectclean.easyhomeexpenses.R
import com.projectclean.easyhomeexpenses.activities.ExpensesActivity
import com.projectclean.easyhomeexpenses.databinding.ExpenseViewBinding
import com.projectclean.easyhomeexpenses.models.Expense
import android.view.ContextMenu
import android.view.View


/**
 * Created by Carlos Albaladejo Pérez on 24/02/2018.
 */

class ExpensesAdapter(var ownerActivity : ExpensesActivity) : RecyclerView.Adapter<ExpensesAdapter.ExpenseViewHolder>()
{
    private var items : List<Expense> = listOf()
    private var mLongSelectedPosition : Int = 0

    class ExpenseViewHolder(var binding: ExpenseViewBinding, var ownerActivity : ExpensesActivity) : RecyclerView.ViewHolder(binding.root),View.OnCreateContextMenuListener
    {
        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        fun bind(expense: Expense)
        {
            binding.setVariable(BR.expense, expense)
            binding.setVariable(BR.activity, ownerActivity)
            binding.executePendingBindings()
        }

        override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
            ownerActivity.menuInflater.inflate(R.menu.expenses_context_menu, menu)

            //Log.d("TEST","TEST")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ExpenseViewHolder
    {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ExpenseViewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.expense_view, parent, false)

        return ExpenseViewHolder(binding, ownerActivity)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int)
    {
        holder.bind(items[position])
        holder.itemView.isLongClickable = true
        holder.itemView.setOnLongClickListener {
                mLongSelectedPosition = position
                false
        }
    }

    override fun getItemCount() = items.size

    fun setElements(items : List<Expense>)
    {
        this.items = items
        notifyDataSetChanged()
    }

    fun getElement(position : Int) : Expense
    {
        return this.items[position]
    }

    fun getLongSelectedItem() : Expense
    {
        return this.items[mLongSelectedPosition]
    }
}