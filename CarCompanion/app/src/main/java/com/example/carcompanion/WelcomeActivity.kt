package com.example.carcompanion

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.carcompanion.databinding.ActivityMainBinding
import com.example.carcompanion.databinding.ActivityWelcomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.firebase.ui.auth.AuthUI

class WelcomeActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    lateinit var authStateListener: FirebaseAuth.AuthStateListener
    private lateinit var binding: ActivityWelcomeBinding

    private val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build()
    )
    private val RC_SIGN_IN = 1

    private var currentActivity: String = "WelcomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Recognize these two lines are new additions to get the project to compile
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setContentView(R.layout.activity_welcome)
        initializeButtonListeners()
        initializeAuthListeners()
    }

    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(authStateListener)
    }

    private fun initializeButtonListeners() {
        // Had to change this from btn_login
        binding.btnLogin.setOnClickListener {
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .setIsSmartLockEnabled(false)
                    .build(), RC_SIGN_IN
            )
        }
        // Had to change this from btn_guest
        binding.btnGuest.setOnClickListener {
            auth.signInAnonymously()
        }
    }

    private fun initializeAuthListeners() {
        authStateListener = FirebaseAuth.AuthStateListener { auth: FirebaseAuth ->
            val user = auth.currentUser
            if(user != null) {
                this.currentActivity = "MainActivity"

                val inputIntent = Intent(this, MainActivity::class.java)
                inputIntent.putExtra(USER_UID, user.uid)
                inputIntent.putExtra(IS_ANON, user.isAnonymous)
                startActivity(inputIntent)
            } else if(this.currentActivity != "WelcomeActivity") {
                this.currentActivity = "WelcomeActivity"
                val intent = Intent(this@WelcomeActivity, this::class.java)
                startActivity(intent)
            }
        }
    }

    companion object {
        const val USER_UID = "USER_UID"
        const val IS_ANON = "IS_ANON"
    }
}