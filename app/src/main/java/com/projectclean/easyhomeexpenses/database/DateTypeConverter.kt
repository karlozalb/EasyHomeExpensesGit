package com.projectclean.easyhomeexpenses.database

import android.arch.persistence.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Carlos Albaladejo Pérez on 12/03/2018.
 */
class DateTypeConverter {

    @TypeConverter
    fun toDate(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun toLong(date: Date?): Long {
        return date!!.time
    }

}