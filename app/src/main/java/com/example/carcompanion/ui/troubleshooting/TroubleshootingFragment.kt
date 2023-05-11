package com.example.carcompanion.ui.troubleshooting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carcompanion.Constants
import com.example.carcompanion.R
import com.example.carcompanion.databinding.FragmentTroubleshootingBinding

class TroubleshootingFragment : Fragment() {
    private lateinit var binding: FragmentTroubleshootingBinding
    private lateinit var troubleAdapter: TroubleAdapter

    private var currentTroubles = ArrayList<TroubleShootingTree.Woe>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTroubleshootingBinding.inflate(inflater, container, false)
        var view = binding.troubleshootingRecycler

        Log.d(Constants.DEFAULT_TAG, "opened troubleshooter")

        troubleAdapter = TroubleAdapter(this)
        binding.troubleshootingRecycler.adapter = troubleAdapter
        binding.troubleshootingRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.troubleshootingRecycler.setHasFixedSize(true)

        setRestartButton(binding)
        setBackStepButton(binding)
        setNextStepButton(binding)
        setViewDiagnosisButton(binding)

        return binding.root
    }

    fun enableNextOption() {

    }

    private fun setUpPageForSymptoms() {
    }

    private fun setUpPageForDiagnoses() {

    }

    fun moveToDiagnosisPage(diagnosis: Diagnosis): Boolean {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, DiagnosisDetailsFragment(diagnosis))
            .addToBackStack(null)
            .commit()
        return true
    }

    private fun setRestartButton(binding: FragmentTroubleshootingBinding) {
        binding.restartButton.isActivated = false
        binding.restartButton.setOnClickListener {
            Log.d(Constants.DEFAULT_TAG, "restart button pressed")
//            adapter.restartTroubleshooting()
        }
    }

    private fun setBackStepButton(binding: FragmentTroubleshootingBinding) {
        binding.backStepButton.isActivated = false
        binding.backStepButton.setOnClickListener {
            Log.d(Constants.DEFAULT_TAG, "back step button pressed")
        }
    }
    private fun setNextStepButton(binding: FragmentTroubleshootingBinding) {
        binding.nextStepButton.isActivated = true
        binding.nextStepButton.setOnClickListener {
            Log.d(Constants.DEFAULT_TAG, "next step button pressed")
            troubleAdapter.changeState("symptoms")
        }
    }

    private fun setViewDiagnosisButton(binding: FragmentTroubleshootingBinding) {
        binding.viewDiagnosisList.isVisible = true
        binding.viewDiagnosisList.setOnClickListener {
            Log.d(Constants.DEFAULT_TAG, "view diagnoses button pressed")
            troubleAdapter.changeState("diagnoses")
        }
    }
}
