package com.projectclean.easyhomeexpenses

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.projectclean.easyhomeexpenses.activities.NewExpenseActivity
import com.projectclean.easyhomeexpenses.fragments.DatePickerFragment

import kotlinx.android.synthetic.main.activity_main.*
import android.arch.persistence.room.Room
import com.projectclean.easyhomeexpenses.database.ExpensesDataBase


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            var intent = Intent(this, NewExpenseActivity::class.java)
            startActivity(intent)
        }

        val db = Room.databaseBuilder(applicationContext, ExpensesDataBase::class.java, "database-name").build()
    }

    fun showTimePickerDialog() {
        val newFragment = DatePickerFragment()
        newFragment.show(supportFragmentManager, "timePicker")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
