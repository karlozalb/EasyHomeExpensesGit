package com.projectclean.easyhomeexpenses.extensions

import android.support.design.widget.Snackbar
import android.text.Editable
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.projectclean.easyhomeexpenses.activities.NewExpenseActivity
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Carlos Albaladejo Pérez on 27/02/2018.
 */

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun EditText.fromMillisToEditable(millis : Long) : Editable
{
    return SpannableStringBuilder(SimpleDateFormat(NewExpenseActivity.DateFormat).format(millis))
}

fun EditText.fromDateToEditable(date : Date) : Editable
{
    return SpannableStringBuilder(SimpleDateFormat(NewExpenseActivity.DateFormat).format(date))
}

inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG) {
    val snack = Snackbar.make(this, message, length)
    snack.show()
}