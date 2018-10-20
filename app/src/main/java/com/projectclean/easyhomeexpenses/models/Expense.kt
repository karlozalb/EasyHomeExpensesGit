package com.projectclean.easyhomeexpenses.models

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.projectclean.easyhomeexpenses.BR
import com.projectclean.easyhomeexpenses.database.ExpenseEntity
import com.projectclean.easyhomeexpenses.database.FirebaseController
import java.util.*

/**
 * Created by Carlos Albaladejo Pérez on 24/02/2018.
 */

class Expense() : BaseObservable()
{

    constructor (pname: String, pdate: Date, pmoney: Float, pownerid: String?, pid : String) : this() {
        this.name = pname
        this.date = pdate
        this.money = pmoney
        this.ownerId = pownerid
        this.id = pid
    }

    constructor(expense: ExpenseEntity) : this(expense.name,expense.date,expense.money.toFloat(),expense.ownerId, "")

    var id : String = "";

    var name : String = ""
    @Bindable
    get() = field

    set(value)
    {
        field = value
        notifyPropertyChanged(BR.name)
    }

    var date : Date = Date()
    @Bindable
    get() = field

    set(value)
    {
        field = value
        notifyPropertyChanged(BR.date)
    }

    var money : Float = 0f
    @Bindable
    get() = field

    set(value)
    {
        field = value
        notifyPropertyChanged(BR.money)
    }

    var ownerId : String? = ""
    @Bindable
    get() = field

    set(value)
    {
        field = value
        notifyPropertyChanged(BR.ownerId)
    }

    var ownerHumanReadableName : String? = ""
        @Bindable
        get() = field

        set(value)
        {
            field = value
            notifyPropertyChanged(BR.ownerHumanReadableName)
        }
}