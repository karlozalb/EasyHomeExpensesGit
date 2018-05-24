package com.projectclean.easyhomeexpenses

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.projectclean.easyhomeexpenses.database.ExpensesDataBase

import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.firestore.FirebaseFirestore
import com.projectclean.easyhomeexpenses.fragments.OfflineListsFragment
import com.projectclean.easyhomeexpenses.fragments.OnlineListsFragment
import android.view.ViewGroup




class MainActivity : AppCompatActivity() {

    val TAG : String = "MainActivity"
    val ANONYMOUS : String = "Anonymous"

    private var mViewPagerAdapter : ViewPagerAdapter? = null

    //Firebase
    private var fireBaseAuth : FirebaseAuth? = null
    private var fireBaseUser : FirebaseUser? = null
    private var mGoogleApiClient : GoogleApiClient? = null
    private var firebaseFirestore : FirebaseFirestore? = null

    private var userName : String? = ""
    private var photoUrl : String? = ""

    private var mOnlineFragment : OnlineListsFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        /*fab.setOnClickListener { view ->
            var intent = Intent(this, NewExpenseActivity::class.java)
            startActivity(intent)
        }*/

        ExpensesDataBase.InitDatabase(this)

        mViewPagerAdapter = ViewPagerAdapter(supportFragmentManager, this)
        view_pager.adapter = mViewPagerAdapter

        tab_layout.setupWithViewPager(view_pager)

        /*mainAdapter = ExpensesAdapter()

        expenses_recycler_view.layoutManager = LinearLayoutManager(this)
        expenses_recycler_view.adapter = mainAdapter

        databaseRequester = ExpenseDatabaseRequester(ExpensesDataBase.instance!!.expenseDao())

        updateAdapter()*/



        /*if (fireBaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
            return
        } else {
            userName = fireBaseUser!!.displayName
            if (fireBaseUser!!.photoUrl != null) {
                photoUrl = fireBaseUser!!.photoUrl.toString()
            }

            firebaseFirestore = FirebaseFirestore.getInstance()

            //testCreateList()
        }*/
    }

    fun setOnlineFragment(onlineFragment : OnlineListsFragment)
    {
        mOnlineFragment = onlineFragment
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
            R.id.action_sign_out -> run {
                mOnlineFragment?.signOut()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}

class ViewPagerAdapter(fm : FragmentManager, private var activity: AppCompatActivity) : FragmentPagerAdapter(fm) {

    private var mCurrentFragment: Fragment? = null

    fun getCurrentFragment(): Fragment? {
        return mCurrentFragment
    }

    override fun setPrimaryItem(container: ViewGroup?, position: Int, `object`: Any) {
        if (getCurrentFragment() !== `object`) {
            mCurrentFragment = `object` as Fragment
        }
        super.setPrimaryItem(container, position, `object`)
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                Log.i("TEST", "getItem = 0")
                return OfflineListsFragment()
            }
            else -> {
                Log.i("TEST", "getItem = 1")
                return OnlineListsFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            0 -> {
                Log.i("TEST", "getPageTitle = " + activity.getString(R.string.offline_list))
                return activity.getString(R.string.offline_list)
            }
            1 -> {
                Log.i("TEST", "getPageTitle = " + activity.getString(R.string.online_list))
                return activity.getString(R.string.online_list)
            }
        }

        return ""
    }
}


