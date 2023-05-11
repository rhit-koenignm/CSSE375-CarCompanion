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
    private lateinit var symptomAdapter: SymptomAdapter

    private var currentSymptoms = ArrayList<TroubleShootingTree.Woe>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTroubleshootingBinding.inflate(inflater, container, false)
        var view = binding.troubleshootingRecycler

        Log.d(Constants.DEFAULT_TAG, "opened troubleshooter")
        getStartingSymptoms()

        symptomAdapter = SymptomAdapter(this, currentSymptoms)
        binding.troubleshootingRecycler.adapter = symptomAdapter
        binding.troubleshootingRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.troubleshootingRecycler.setHasFixedSize(true)

        setRestartButton(binding);
        setBackStepButton(binding);
        setNextStepButton(binding);

        return binding.root
    }

    fun moveToDiagnosisPage(diagnosis: Diagnosis): Boolean {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, DiagnosisDetailsFragment(diagnosis))
            .addToBackStack(null)
            .commit()
        return true
    }

    fun addFrag(f: Fragment): Boolean {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, f)
            .addToBackStack(null)
            .commit()
        return true
    }

    private fun getStartingSymptoms() {
        currentSymptoms = TroubleTreeUtils.createSymptoms()
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
        binding.nextStepButton.isActivated = false
        binding.nextStepButton.setOnClickListener {
            Log.d(Constants.DEFAULT_TAG, "next step button pressed")
        }
    }

    private fun setViewDiagnosisButton(binding: FragmentTroubleshootingBinding) {
        binding.viewDiagnosisList.isVisible = false
        binding.viewDiagnosisList.setOnClickListener {

        }
    }
}
