package com.projectclean.easyhomeexpenses.extensions

import android.databinding.BindingAdapter
import android.widget.TextView

/**
 * Created by Carlos Albaladejo Pérez on 04/03/2018.
 */

@BindingAdapter("android:text")
fun setFloatText(view: TextView, amount: Float)
{
    view.text = amount.toString()
}