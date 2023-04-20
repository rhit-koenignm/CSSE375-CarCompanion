package com.example.carcompanion.ui.user_auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.carcompanion.Constants
import com.example.carcompanion.databinding.FragmentFrontPageBinding

class FrontPageFragment : Fragment() {

    private lateinit var binding: FragmentFrontPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        binding = FragmentFrontPageBinding.inflate(layoutInflater)


        return binding.root
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
//            auth.signInAnonymously()
        }
    }

}