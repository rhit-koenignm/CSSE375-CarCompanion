package com.example.carcompanion.ui.user_auth

import UserViewModel
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.carcompanion.Constants
import com.example.carcompanion.R
import com.example.carcompanion.WelcomeActivity
import com.example.carcompanion.databinding.FragmentLoginBinding
import com.example.carcompanion.databinding.FragmentSignupBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.util.ui.fieldvalidators.EmailFieldValidator
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

            val userModel = ViewModelProvider(this).get(UserViewModel::class.java)
            userModel.getOrMakeUser {
                if (userModel.hasCompletedSetup()) {
                      Log.d(Constants.AUTH_TAG, "User has completed setup")
                } else {
                    // ...

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

            if(TextUtils.isEmpty(email) || !isValidEmail(email)) {
                binding.emailInput.error = "Email is required"
                return@setOnClickListener
            }

            if(TextUtils.isEmpty(firstName)) {
                binding.fnameInput.error = "First name is required"
                return@setOnClickListener
            }

            if(TextUtils.isEmpty(lastName)) {
                binding.lnameInput.error = "Last name is required"
                return@setOnClickListener
            }

            if(TextUtils.isEmpty(password)) {
                binding.choosePasswordInput.error = "Password is required"
                return@setOnClickListener
            }

            if(TextUtils.isEmpty(confirmPassword)) {
                binding.retypePasswordInput.error = "Please retype your password"
                return@setOnClickListener
            }

            if(password != confirmPassword) {
                binding.choosePasswordInput.error = "Passwords don't match"
                binding.retypePasswordInput.error = "Passwords don't match"
                return@setOnClickListener
            }

            // In this case, we know we've got a valid login
            var auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener() {   task ->
                    if(task.isSuccessful) {
                        Toast.makeText(this.context, "Sign up successful", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this.context, "Sign up failed", Toast.LENGTH_SHORT).show()
                    }
                    val userModel = ViewModelProvider(this).get(UserViewModel::class.java)
                    userModel.getOrMakeUser {
                        Log.d(Constants.AUTH_TAG, "Get or make user called")
                    }
                }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        return email.matches(emailRegex.toRegex())
    }
}
