package com.example.carcompanion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.carcompanion.databinding.ActivityMainBinding
import com.example.carcompanion.databinding.ActivityWelcomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.firebase.ui.auth.AuthUI

class WelcomeActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    lateinit var authStateListener: FirebaseAuth.AuthStateListener

    private lateinit var binding: ActivityWelcomeBinding

    private var currentActivity: String = "WelcomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeButtonListeners()
        initializeAuthListeners()
    }

    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(authStateListener)
    }

    private fun initializeButtonListeners() {
        binding.btnLogin.setOnClickListener {
            // TODO: Move to sign in fragment
            Log.d(Constants.DEFAULT_TAG, " TODO: Navigate to sign in fragment")
        }

        binding.btnSignup.setOnClickListener {
            // TODO: Move to sign up fragment
            Log.d(Constants.DEFAULT_TAG, " TODO: Navigate to sign up fragment")
        }

        binding.btnGuest.setOnClickListener {
            Log.d(Constants.DEFAULT_TAG, " Sign in as guest option selected -> navigating to main activity")
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