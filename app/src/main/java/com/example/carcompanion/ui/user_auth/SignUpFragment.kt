package com.example.carcompanion.ui.user_auth

import UserViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.carcompanion.Constants
import com.example.carcompanion.R
import com.example.carcompanion.databinding.FragmentLoginBinding
import com.example.carcompanion.databinding.FragmentSignupBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    val signinLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { /* empty since the auth listener already responds .*/ }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignupBinding.bind(view)
        initializeAuthenticationListener()
        setUpSignUpButtonListener()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    private fun initializeAuthenticationListener() {
        authStateListener = FirebaseAuth.AuthStateListener { auth: FirebaseAuth ->
            val user = auth.currentUser

            if (user == null) {
//                setupAuthUI()
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

    private fun setUpSignUpButtonListener() {
        Log.d(Constants.AUTH_TAG, "Initializing sign-up button")
        binding.btnSignup.setOnClickListener {
            Log.d(Constants.AUTH_TAG, "Sign up button pressed")
            //TODO: Check inputs and then attempt to sign up
            val email = binding.emailInput.text.toString().trim()
            val firstName = binding.fnameInput.text.toString().trim()
            val lastName = binding.lnameInput.text.toString().trim()
            val password = binding.choosePasswordInput.text.toString().trim()
            val confirmPassword = binding.retypePasswordInput.text.toString().trim()


//            if(TextUtils.)
        }
    }




}
