package com.example.carcompanion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.example.carcompanion.databinding.ActivityWelcomeBinding
import com.example.carcompanion.ui.user_auth.FrontPageFragment
import com.google.firebase.auth.FirebaseAuth
import com.firebase.ui.auth.AuthUI
import com.google.android.material.navigation.NavigationView

class WelcomeActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    lateinit var authStateListener: FirebaseAuth.AuthStateListener

    private lateinit var binding: ActivityWelcomeBinding

    private var currentActivity: String = "WelcomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeAuthListeners()

        Log.d(Constants.DEFAULT_TAG, "Attempting to add Front page Fragment")
        addFrag(FrontPageFragment())
    }

    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(authStateListener)
    }


    private fun initializeAuthListeners() {
        authStateListener = FirebaseAuth.AuthStateListener { auth: FirebaseAuth ->
            val user = auth.currentUser
            if (user != null) {
                this.currentActivity = "MainActivity"
                val inputIntent = Intent(this, MainActivity::class.java)
                inputIntent.putExtra(USER_UID, user.uid)
                inputIntent.putExtra(IS_ANON, user.isAnonymous)
                startActivity(inputIntent)
            } else if (this.currentActivity != "WelcomeActivity") {
                this.currentActivity = "WelcomeActivity"
                val intent = Intent(this@WelcomeActivity, this::class.java)
                startActivity(intent)
            }
        }
    }

    fun addFrag(f: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, f)
            .addToBackStack(null)
            .commit()
        return true
    }

    fun switchFrag(f: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, f)
            .commit()
        return true
    }

    companion object {
        const val USER_UID = "USER_UID"
        const val IS_ANON = "IS_ANON"
    }
}