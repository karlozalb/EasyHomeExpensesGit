package com.projectclean.easyhomeexpenses.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Created by Carlos Albaladejo Pérez on 12/03/2018.
 */

@Database(entities = arrayOf(ExpenseEntity::class), version = 1, exportSchema = false)
abstract class ExpensesDataBase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseEntityDao
}