package com.example.carcompanion.ui.user_auth

import UserViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.carcompanion.R
import com.example.carcompanion.database.models.UserObject
import com.example.carcompanion.databinding.FragmentLoginBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    val signinLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { /* empty since the auth listener already responds .*/ }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        initializeAuthenticationListener()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    private fun initializeAuthenticationListener() {
        authStateListener = FirebaseAuth.AuthStateListener { auth: FirebaseAuth ->
            val user = auth.currentUser

            if (user == null) {
                setupAuthUI()
            } else {
                val userModel = ViewModelProvider(this).get(UserViewModel::class.java)
                userModel.getOrMakeUser {
                    if (userModel.hasCompletedSetup()) {
//                        setupHeaderBar(userModel)

                    } else {
                        // ...
                    }
                }
            }
        }
    }

    private fun setupAuthUI() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        val signinIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setIsSmartLockEnabled(false)
            .build()
        signinLauncher.launch(signinIntent)
    }
}