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
import com.example.carcompanion.ui.troubleshooting.TroubleshootingFlowController.State
import kotlinx.coroutines.flow.flow

class TroubleshootingFragment(val controller: TroubleshootingFlowController?) : Fragment() {
    private lateinit var binding: FragmentTroubleshootingBinding
    private lateinit var troubleAdapter: TroubleAdapter
    private lateinit var flowController: TroubleshootingFlowController

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTroubleshootingBinding.inflate(inflater, container, false)

        Log.d(Constants.DEFAULT_TAG, "opened troubleshooter")

        if(controller == null) {
            flowController = TroubleshootingFlowController()
        } else {
            flowController = controller
        }

        troubleAdapter = TroubleAdapter(this, flowController)
        binding.troubleshootingRecycler.adapter = troubleAdapter
        binding.troubleshootingRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.troubleshootingRecycler.setHasFixedSize(true)

        setRestartButton()
        setBackStepButton()
        setNextStepButton()
        setViewDiagnosisButton()

        updateButtonsFromState()

        return binding.root
    }

    fun updateButtonsFromState() {
        when(flowController.state) {
            is State.Start -> {
                binding.restartButton.isEnabled = false
                binding.backStepButton.isEnabled = false
                binding.nextStepButton.isEnabled = false

                binding.viewDiagnosisList.visibility = View.GONE
                binding.viewDiagnosisList.isEnabled = false
            }
            is State.PrimarySelected -> {
                binding.restartButton.isEnabled = false
                binding.backStepButton.isEnabled = false
                binding.nextStepButton.isEnabled = false

                binding.viewDiagnosisList.visibility = View.GONE
                binding.viewDiagnosisList.isEnabled = false
            }
            is State.SecondarySelected -> {
                binding.restartButton.isEnabled = true
                binding.backStepButton.isEnabled = true
                binding.nextStepButton.isEnabled = false

                binding.viewDiagnosisList.visibility = View.GONE
                binding.viewDiagnosisList.isEnabled = false
            }
            is State.ViewDiagnosis -> {
                binding.restartButton.isEnabled = true
                binding.backStepButton.isEnabled = true
                binding.nextStepButton.isEnabled = false

                binding.viewDiagnosisList.visibility = View.VISIBLE
                binding.viewDiagnosisList.isEnabled = true
            }
        }
    }


    private fun setRestartButton() {
        binding.restartButton.isEnabled = false
        binding.restartButton.setOnClickListener {
            Log.d(Constants.DEFAULT_TAG, "restart button pressed")
//            adapter.restartTroubleshooting()
        }
    }

    private fun setBackStepButton() {
        binding.backStepButton.isEnabled = false
        binding.backStepButton.setOnClickListener {
            Log.d(Constants.DEFAULT_TAG, "back step button pressed")
        }
    }
    private fun setNextStepButton() {
        binding.nextStepButton.isEnabled = false
        binding.nextStepButton.setOnClickListener {
            Log.d(Constants.DEFAULT_TAG, "next step button pressed")
            binding.troubleshootingRecycler.adapter = troubleAdapter
        }
    }

    private fun setViewDiagnosisButton() {
        binding.viewDiagnosisList.visibility = View.GONE
        binding.viewDiagnosisList.isEnabled = false
        binding.viewDiagnosisList.setOnClickListener {
            Log.d(Constants.DEFAULT_TAG, "view diagnoses button pressed")
            binding.troubleshootingRecycler.adapter = troubleAdapter
        }
    }

    fun moveToDiagnosisPage(diagnosis: Diagnosis): Boolean {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, DiagnosisDetailsFragment(diagnosis, flowController))
            .addToBackStack(null)
            .commit()
        return true
    }
}
