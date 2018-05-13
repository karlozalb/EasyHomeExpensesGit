package com.projectclean.easyhomeexpenses.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * Created by Carlos Albaladejo Pérez on 12/03/2018.
 */

@Entity(tableName = "expenses")
class ExpenseEntity
{

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0

    var name: String = ""
    var ownerName: String = ""
    var date: Date = Date()
    var money: String = "0.0"

}

@Entity(tableName = "lists")
class ListEntity
{

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0

    var name: String = ""
    var ownerName: String = ""

}