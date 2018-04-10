package com.projectclean.easyhomeexpenses.models

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.projectclean.easyhomeexpenses.BR
import com.projectclean.easyhomeexpenses.database.ExpenseEntity
import java.util.*

/**
 * Created by Carlos Albaladejo Pérez on 24/02/2018.
 */

data class Expense(var pname: String, var pdate: Date, var pmoney: Float, var pownername: String) : BaseObservable()
{

    constructor(expense: ExpenseEntity) : this(expense.name,expense.date,expense.money.toFloat(),expense.ownerName)

    var name : String = pname
    @Bindable
    get() = field

    set(value)
    {
        name = value
        notifyPropertyChanged(BR.name)
    }

    var date : Date = pdate
    @Bindable
    get() = field

    set(value)
    {
        date = value
        notifyPropertyChanged(BR.date)
    }

    var money : Float = pmoney
    @Bindable
    get() = field

    set(value)
    {
        money = value
        notifyPropertyChanged(BR.money)
    }

    var ownerName : String = pownername
    @Bindable
    get() = field

    set(value)
    {
        ownerName = value
        notifyPropertyChanged(BR.name)
    }
}