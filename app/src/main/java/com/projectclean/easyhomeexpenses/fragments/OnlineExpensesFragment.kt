package com.projectclean.easyhomeexpenses.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

import com.projectclean.easyhomeexpenses.R
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.online_list_fragment.*

class OnlineExpensesFragment : Fragment(), GoogleApiClient.OnConnectionFailedListener {

    companion object {

        private val TAG = "SignInActivity"
        private val RC_SIGN_IN = 9001
    }

    private var mSignInButton: SignInButton? = null
    private var mGoogleApiClient: GoogleApiClient? = null

    // Firebase instance variables
    private var mFirebaseAuth: FirebaseAuth? = null
    private var firebaseFirestore : FirebaseFirestore? = null

    private var fireBaseUser : FirebaseUser? = null
    private var userName : String? = ""
    private var photoUrl : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(R.layout.online_list_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mFirebaseAuth = FirebaseAuth.getInstance()
        fireBaseUser = mFirebaseAuth!!.currentUser

        if (fireBaseUser == null) {
            // Not signed in, launch the Sign In activity
            // Assign fields
            mSignInButton = sign_in_button

            // Set click listeners
            mSignInButton!!.setOnClickListener({ _ -> signIn()})

            // Configure Google Sign In
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()

            mGoogleApiClient = GoogleApiClient.Builder(context)
                    .enableAutoManage(activity /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build()

            sign_in_layout.visibility = View.VISIBLE
            signed_in_layout.visibility = View.GONE
        } else {
            userName = fireBaseUser!!.displayName
            if (fireBaseUser!!.photoUrl != null) {
                photoUrl = fireBaseUser!!.photoUrl.toString()
            }

            firebaseFirestore = FirebaseFirestore.getInstance()

            sign_in_layout.visibility = View.GONE
            signed_in_layout.visibility = View.VISIBLE
            //testCreateList()
        }

        // Initialize FirebaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onPause() {
        super.onPause()
        mGoogleApiClient!!.stopAutoManage(activity)
        mGoogleApiClient!!.disconnect()
    }

    fun signIn(){
        var intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(intent, RC_SIGN_IN)
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
                        sign_in_layout.visibility = View.GONE
                        signed_in_layout.visibility = View.VISIBLE
                    }
                }
    }

}
