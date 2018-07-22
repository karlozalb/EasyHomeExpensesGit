package com.projectclean.easyhomeexpenses.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.ListFragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.projectclean.easyhomeexpenses.MainActivity
import com.projectclean.easyhomeexpenses.R
import com.projectclean.easyhomeexpenses.activities.ExpensesActivity
import com.projectclean.easyhomeexpenses.adapters.ExpenseListsAdapter
import com.projectclean.easyhomeexpenses.database.FirebaseController
import com.projectclean.easyhomeexpenses.models.ExpenseList
import kotlinx.android.synthetic.main.online_list_fragment.*

class OnlineListsFragment : ExpensesListFragment(), GoogleApiClient.OnConnectionFailedListener, NewListFragment.NoticeDialogListener {

    companion object {


        private val TAG = "SignInActivity"
        private val RC_SIGN_IN = 9001
        private val ANONYMOUS : String = "Anonymous"
    }

    private var mGoogleApiClient: GoogleApiClient? = null

    // Firebase instance variables
    private var mFirebaseAuth: FirebaseAuth? = null
    private var mFirebaseFirestore : FirebaseFirestore? = null

    private var mFireBaseUser : FirebaseUser? = null
    private var mUserName : String? = ""
    private var mPhotoUrl : String? = ""

    private var mListId : String? = ""

    //ExpenseList adapter
    private lateinit var mMainAdapter : ExpenseListsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(R.layout.online_list_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setOnlineFragment(this)

        mFirebaseAuth = FirebaseAuth.getInstance()
        mFireBaseUser = mFirebaseAuth!!.currentUser

        if (mFireBaseUser == null) {
            sign_in_button.setOnClickListener({ _ -> signIn()})

            setUISignedIn(false)
        } else {
            mUserName = mFireBaseUser!!.displayName
            if (mFireBaseUser!!.photoUrl != null) {
                mPhotoUrl = mFireBaseUser!!.photoUrl.toString()
            }

            onSignedIn()
        }

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(context)
                .enableAutoManage(activity /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

        // Initialize FirebaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance()

        add_online_list_btn.setOnClickListener { createNewListDialog() }
    }

    override fun onPause() {
        super.onPause()
        mGoogleApiClient!!.stopAutoManage(activity)
        mGoogleApiClient!!.disconnect()
    }

    override fun onResume() {
        super.onResume()

        mGoogleApiClient!!.connect()
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:$connectionResult")
        Toast.makeText(context, "Google Play Services error.", Toast.LENGTH_SHORT).show()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                // Google Sign-In was successful, authenticate with Firebase
                val account = result.signInAccount
                firebaseAuthWithGoogle(account!!)
            } else {
                // Google Sign-In failed
                Log.e(TAG, "Google Sign-In failed.")
            }
        }
    }

    private fun signIn(){
        var intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(intent, RC_SIGN_IN)
    }

    private fun onSignedIn()
    {
        setUISignedIn(true)

        mFireBaseUser = mFirebaseAuth!!.currentUser
        mFirebaseFirestore = FirebaseFirestore.getInstance()

        FirebaseController.init(mFirebaseFirestore!!,mFireBaseUser!!)

        mMainAdapter = ExpenseListsAdapter(this)

        lists_recycler_view.layoutManager = LinearLayoutManager(context)
        lists_recycler_view.adapter = mMainAdapter

        mMainAdapter.updateContents()
    }

    fun signOut()
    {
        mFirebaseAuth!!.signOut()
        Auth.GoogleSignInApi.signOut(mGoogleApiClient)
        mUserName = ANONYMOUS

        setUISignedIn(false)
    }

    private fun setUISignedIn(signedIn : Boolean)
    {
        sign_in_layout.visibility = if(signedIn) View.GONE; else View.VISIBLE;
        signed_in_layout.visibility = if(signedIn) View.VISIBLE; else View.GONE;
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGooogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)

        mFirebaseAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(activity) { task  ->
                    Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful)

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful) {
                        Log.w(TAG, "signInWithCredential", task.exception)
                        Toast.makeText(context, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    } else {
                        onSignedIn()
                    }
                }
    }

    fun createNewListDialog()
    {
        val newFragment = NewListFragment()
        newFragment.show(childFragmentManager, "NewListFragment")
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, listName: String) {
        FirebaseController.instance!!.createNewList(listName,
                {
                    listId ->
                    run{ mListId = listId;Log.i(TAG, "ExpenseList created successfully with id: " + mListId) }
                },
                {
                    Log.e(TAG, "Error creating a new list.")
                })
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
    }

    override fun onItemClicked(list: ExpenseList)
    {
        Log.e(TAG, "list:"+list.listId)

        var intent = Intent(context, ExpensesActivity::class.java)
        intent.putExtra(ExpensesActivity.ONLINE_MODE, true)
        intent.putExtra(ExpensesActivity.LIST_ID, list.listId)
        startActivity(intent)
    }
}
