package com.projectclean.easyhomeexpenses.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import com.projectclean.easyhomeexpenses.R
import kotlinx.android.synthetic.main.new_list_fragment.*
import kotlinx.android.synthetic.main.new_list_fragment.view.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [NewListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [NewListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewListFragment : DialogFragment() {

    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment, listName: String)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    var mListener : NoticeDialogListener? = null
    lateinit var mListNameText : EditText

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        mListener = parentFragment as NoticeDialogListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var builder = AlertDialog.Builder(activity)
        var inflater = activity.layoutInflater

        var view = inflater.inflate(R.layout.new_list_fragment, null)
        mListNameText = view.list_name_et

        builder.setView(view)
                .setPositiveButton(R.string.ok, { dialogInterface: DialogInterface, i: Int ->
                    mListener!!.onDialogPositiveClick(this, mListNameText!!.text.toString())
                })
                .setNegativeButton(R.string.cancel, { dialogInterface: DialogInterface, i: Int ->
                    this@NewListFragment.dialog.cancel()
                    mListener!!.onDialogNegativeClick(this)
                })


        return builder.create()
    }

}
