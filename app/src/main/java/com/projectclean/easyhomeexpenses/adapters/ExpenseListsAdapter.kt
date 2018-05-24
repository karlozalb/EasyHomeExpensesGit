package com.projectclean.easyhomeexpenses.adapters

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.projectclean.easyhomeexpenses.BR
import com.projectclean.easyhomeexpenses.R
import com.projectclean.easyhomeexpenses.database.FirebaseController
import com.projectclean.easyhomeexpenses.databinding.ListViewBinding
import com.projectclean.easyhomeexpenses.fragments.OnlineListsFragment
import com.projectclean.easyhomeexpenses.models.ExpenseList

/**
 * Created by Carlos Albaladejo Pérez on 24/02/2018.
 */

class ExpenseListsAdapter(var ownerFragment: OnlineListsFragment) : RecyclerView.Adapter<ExpenseListsAdapter.ListViewHolder>()
{
    private var items : List<ExpenseList> = listOf()

    class ListViewHolder(var binding: ListViewBinding, var ownerFragment: OnlineListsFragment) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(expenseList: ExpenseList)
        {
            binding.setVariable(BR.list, expenseList)
            binding.setVariable(BR.fragment, ownerFragment)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ListViewHolder
    {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ListViewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_view, parent, false)

        return ListViewHolder(binding, ownerFragment)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    fun updateContents(){
        FirebaseController.instance?.getLists(
            {list -> run {
                Log.d("OnlineFragment","updateContents SUCCESSFUL.")
                this.items = list
                notifyDataSetChanged()
            }}
        )
    }

}