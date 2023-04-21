package com.example.carcompanion.ui.user_auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.carcompanion.Constants
import com.example.carcompanion.R
import com.example.carcompanion.databinding.FragmentFrontPageBinding
import com.google.firebase.auth.FirebaseAuth

class FrontPageFragment : Fragment() {

    private lateinit var binding: FragmentFrontPageBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFrontPageBinding.bind(view)
        initializeButtonListeners()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_front_page, container, false)
    }

    private fun initializeButtonListeners() {
        Log.d(Constants.DEFAULT_TAG, "Initializing buttons")
        binding.btnLogin.setOnClickListener {
            // TODO: Move to sign in fragment
            Log.d(Constants.DEFAULT_TAG, " TODO: Navigate to log in fragment")
            addFrag(LoginFragment())
        }

        binding.btnSignup.setOnClickListener {
            // TODO: Move to sign up fragment
            Log.d(Constants.DEFAULT_TAG, " TODO: Navigate to sign up fragment")
            addFrag(SignUpFragment())
        }

        binding.btnGuest.setOnClickListener {
            Log.d(
                Constants.DEFAULT_TAG,
                " Sign in as guest option selected -> navigating to main activity"
            )
            FirebaseAuth.getInstance().signInAnonymously()
        }
    }

    private fun switchFrag(f: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, f)
            .addToBackStack(null) // This is optional, but allows for back navigation
            .commit()
    }
    fun addFrag(f: Fragment): Boolean {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, f)
            .addToBackStack(null)
            .commit()
        return true
    }
}