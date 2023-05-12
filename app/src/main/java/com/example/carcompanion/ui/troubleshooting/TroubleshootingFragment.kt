package com.example.carcompanion.ui.troubleshooting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carcompanion.Constants
import com.example.carcompanion.R
import com.example.carcompanion.databinding.FragmentTroubleshootingBinding
import com.example.carcompanion.ui.troubleshooting.TroubleshootingFlowController.State
import com.example.carcompanion.ui.troubleshooting.TroubleshootingFlowController.Event

class TroubleshootingFragment(val controller: TroubleshootingFlowController?) : Fragment() {
    private lateinit var binding: FragmentTroubleshootingBinding
    private lateinit var troubleAdapter: TroubleAdapter
    private lateinit var flowController: TroubleshootingFlowController

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTroubleshootingBinding.inflate(inflater, container, false)

        Log.d(Constants.TRBLE_FRAG, "opened troubleshooter")

        if(controller == null) {
            flowController = TroubleshootingFlowController()
        } else {
            flowController = controller
        }

        troubleAdapter = TroubleAdapter(this, flowController)
        binding.troubleshootingRecycler.adapter = troubleAdapter
        binding.troubleshootingRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.troubleshootingRecycler.setHasFixedSize(true)

        setUpRestartButton()
        setUpBackStepButton()
        setUpNextStepButton()
        setUpViewDiagnosisButton()

        updateButtonsFromState()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun toggleNextButton(isClickable: Boolean) {
        if(isClickable) {
            binding.nextStepButton.isEnabled = true
            binding.nextStepButton.alpha = 1.0f
        } else {
            binding.nextStepButton.isEnabled = false
            binding.nextStepButton.alpha = 0.5f
        }
    }

    fun toggleBackButton(isClickable: Boolean) {
        if(isClickable) {
            binding.backStepButton.isEnabled = true
            binding.backStepButton.alpha = 1.0f
        } else {
            binding.backStepButton.isEnabled = false
            binding.backStepButton.alpha = 0.5f
        }
    }

    fun toggleRestartButton(isClickable: Boolean) {
        if(isClickable) {
            binding.restartButton.isEnabled = true
            binding.restartButton.alpha = 1.0f
        } else {
            binding.restartButton.isEnabled = false
            binding.restartButton.alpha = 0.5f
        }
    }

    fun toggleViewDiagnosisButton(isClickable: Boolean) {
        if(isClickable) {
            binding.viewDiagnosisList.visibility = View.VISIBLE
            binding.viewDiagnosisList.isEnabled = true
            binding.viewDiagnosisList.alpha = 1.0f
        } else {
            binding.viewDiagnosisList.visibility = View.GONE
            binding.viewDiagnosisList.isEnabled = false
            binding.viewDiagnosisList.alpha = 0.5f
        }
    }


    fun updateButtonsFromState() {
        when(flowController.state) {
            is State.Start -> {
                toggleNextButton(false)
                toggleBackButton(false)
                toggleRestartButton(false)
                toggleViewDiagnosisButton(false)
            }
            is State.PrimarySelected -> {
                toggleNextButton(false)
                toggleBackButton(true)
                toggleRestartButton(true)
                toggleViewDiagnosisButton(false)
            }
            is State.SecondarySelected -> {
                toggleNextButton(false)
                toggleBackButton(true)
                toggleRestartButton(true)
                toggleViewDiagnosisButton(false)
            }
            is State.ViewDiagnosis -> {
                toggleNextButton(false)
                toggleBackButton(true)
                toggleRestartButton(true)
                toggleViewDiagnosisButton(false)
            }
        }
    }


    private fun setUpRestartButton() {
        toggleRestartButton(false)
        binding.restartButton.setOnClickListener {
            Log.d(Constants.TRBLE_FRAG, "restart button pressed")
            troubleAdapter.restart()
        }
    }

    private fun setUpBackStepButton() {
        toggleBackButton(false)
        binding.backStepButton.setOnClickListener {
            Log.d(Constants.TRBLE_FRAG, "back step button pressed")
            troubleAdapter.backStep()
        }
    }
    private fun setUpNextStepButton() {
        toggleNextButton(false)
        binding.nextStepButton.setOnClickListener {
            Log.d(Constants.TRBLE_FRAG, "next step button pressed")
            var didStep = troubleAdapter.nextStep()
            if(didStep == false) {
                val toast = Toast.makeText(context,"Next step failed, no trouble selected?", Toast.LENGTH_LONG)
                toast.show()
            } else {
                moveToNextPage()
            }
        }
    }

    private fun setUpViewDiagnosisButton() {
        toggleViewDiagnosisButton(false)
        binding.viewDiagnosisList.setOnClickListener {
            Log.d(Constants.TRBLE_FRAG, "view diagnoses button pressed")
        }
    }

    fun moveToDiagnosisPage(diagnosis: Diagnosis): Boolean {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, DiagnosisDetailsFragment(diagnosis, flowController))
            .addToBackStack(null)
            .commit()
        return true
    }

    fun moveToNextPage(): Boolean {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, TroubleshootingFragment(flowController))
            .addToBackStack(null)
            .commit()
        return true
    }
}
