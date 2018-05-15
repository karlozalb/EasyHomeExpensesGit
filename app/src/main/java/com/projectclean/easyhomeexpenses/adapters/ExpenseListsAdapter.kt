package com.projectclean.easyhomeexpenses.adapters

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
import com.projectclean.easyhomeexpenses.R
import com.projectclean.easyhomeexpenses.database.FirebaseController
import com.projectclean.easyhomeexpenses.databinding.ListViewBinding
import com.projectclean.easyhomeexpenses.models.ExpenseList

/**
 * Created by Carlos Albaladejo Pérez on 24/02/2018.
 */

class ExpenseListsAdapter : RecyclerView.Adapter<ExpenseListsAdapter.ListViewHolder>()
{
    private var items : List<ExpenseList> = listOf()

    class ListViewHolder(var binding: ListViewBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(expenseList: ExpenseList)
        {
            binding.setVariable(BR.expense, expenseList)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ListViewHolder
    {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ListViewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_view, parent, false)

        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    fun updateContents(){
        FirebaseController.instance?.getLists(
            {list -> run {
                this.items = list
                notifyDataSetChanged()
            }}
        )
    }

}