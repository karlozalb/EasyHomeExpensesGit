package com.projectclean.easyhomeexpenses.database

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE

/**
 * Created by Carlos Albaladejo Pérez on 12/03/2018.
 */

@Dao
interface ExpenseEntityDao {

    @Query("select * from task")
    fun getAllExpenses(): List<ExpenseEntity>

    @Query("select * from task where id = :id")
    fun findExpenseById(id: Long): ExpenseEntity

    @Insert(onConflict = REPLACE)
    fun insertExpense(task: ExpenseEntity)

    @Update(onConflict = REPLACE)
    fun updateExpense(task: ExpenseEntity)

    @Delete
    fun deleteExpense(task: ExpenseEntity)
}