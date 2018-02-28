package com.projectclean.easyhomeexpenses.models

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.projectclean.easyhomeexpenses.BR

/**
 * Created by Carlos Albaladejo Pérez on 24/02/2018.
 */

data class Expense(var pname: String) : BaseObservable()
{
    var name : String = pname
    @Bindable
    get() = field

    set(value)
    {
        name = value
        notifyPropertyChanged(BR.expense)
    }
}