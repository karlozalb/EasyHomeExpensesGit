package com.projectclean.easyhomeexpenses.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.NumberPicker

import com.projectclean.easyhomeexpenses.R
import kotlinx.android.synthetic.main.new_list_fragment.*
import kotlinx.android.synthetic.main.new_list_fragment.view.*
import java.lang.Integer.parseInt

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [NewListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [NewListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewListFragment : DialogFragment() {

    companion object {
        val TAG = "NewListFragment"
    }

    interface NewListDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment, listName: String, listPeriodStart: Int)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    var mListener : NewListDialogListener? = null
    lateinit var mListNameText : EditText
    lateinit var mPeriodStartEnd : EditText

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        mListener = parentFragment as NewListDialogListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var builder = AlertDialog.Builder(activity)
        var inflater = activity.layoutInflater

        var view = inflater.inflate(R.layout.new_list_fragment, null)
        mListNameText = view.list_name_et
        mPeriodStartEnd = view.period_startend_et

        view.period_startend_et.setOnClickListener {
            createNumberPickerDialog(mPeriodStartEnd)
        }

        //We need to set this to avoid the keyboard appearance.
        view.period_startend_et.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                createNumberPickerDialog(mPeriodStartEnd)
            }
        }

        builder.setView(view)
                .setPositiveButton(R.string.ok, { dialogInterface: DialogInterface, i: Int ->
                    mListener?.onDialogPositiveClick(this, mListNameText?.text.toString(), mPeriodStartEnd?.text.toString().toInt())
                })
                .setNegativeButton(R.string.cancel, { dialogInterface: DialogInterface, i: Int ->
                    this@NewListFragment.dialog.cancel()
                    mListener?.onDialogNegativeClick(this)
                })


        return builder.create()
    }

    private fun createNumberPickerDialog(editText: EditText)
    {
        val builder = AlertDialog.Builder(activity)

        var numberPicker = NumberPicker(activity)
        builder.setView(numberPicker)
        numberPicker.maxValue = 31
        numberPicker.minValue = 0

        builder.setPositiveButton(R.string.ok,      { dialog, id -> run{
            editText.setText(numberPicker.value.toString())}
            Log.d(TAG,"numberPicker.value:"+numberPicker.value)
            Log.d(TAG,"editText.text:"+editText.text)
        })
        builder.setNegativeButton(R.string.cancel,  { dialog, id -> Log.d(TAG,"")})

        val dialog = builder.create()

        dialog.show()
    }
}
