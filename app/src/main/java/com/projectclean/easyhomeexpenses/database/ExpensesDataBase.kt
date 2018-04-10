package com.projectclean.easyhomeexpenses.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context

/**
 * Created by Carlos Albaladejo Pérez on 12/03/2018.
 */

@Database(entities = arrayOf(ExpenseEntity::class), version = 1, exportSchema = false)
@TypeConverters(DateTypeConverter::class)
abstract class ExpensesDataBase : RoomDatabase() {

    companion object {

        var instance : ExpensesDataBase? = null

        fun InitDatabase(context : Context)
        {
            if (instance == null)
                instance = Room.databaseBuilder(context, ExpensesDataBase::class.java, "database-name").build()
        }
    }

    abstract fun expenseDao(): ExpenseEntityDao

}