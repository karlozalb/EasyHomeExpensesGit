package com.projectclean.easyhomeexpenses.database

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE

/**
 * Created by Carlos Albaladejo Pérez on 12/03/2018.
 */

@Dao
interface ExpenseEntityDao {

    @Query("select * from expenses")
    fun getAllExpenses(): List<ExpenseEntity>

    @Query("select * from expenses where id = :id")
    fun findExpenseById(id: Long): ExpenseEntity

    @Insert(onConflict = REPLACE)
    fun insertExpense(expense: ExpenseEntity)

    @Update(onConflict = REPLACE)
    fun updateExpense(expense: ExpenseEntity)

    @Delete
    fun deleteExpense(expense: ExpenseEntity)
}