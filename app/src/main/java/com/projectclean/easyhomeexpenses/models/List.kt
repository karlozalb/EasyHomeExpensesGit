package com.projectclean.easyhomeexpenses.models

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.projectclean.easyhomeexpenses.BR
import com.projectclean.easyhomeexpenses.database.ExpenseEntity
import com.projectclean.easyhomeexpenses.database.ListEntity
import java.util.*

/**
 * Created by Carlos Albaladejo Pérez on 24/02/2018.
 */

data class List(var pname: String, var pownername: String) : BaseObservable()
{

    constructor(list: ListEntity) : this(list.name, list.ownerName)

    var name : String = pname
    @Bindable
    get() = field

    set(value)
    {
        name = value
        notifyPropertyChanged(BR.name)
    }

    var ownerName : String = pownername
    @Bindable
    get() = field

    set(value)
    {
        ownerName = value
        notifyPropertyChanged(BR.ownerName)
    }
}