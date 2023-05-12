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

    fun updateButtonsFromState() {
        when(flowController.state) {
            is State.Start -> {
                binding.restartButton.isActivated = true
                binding.restartButton.isEnabled = false
                binding.backStepButton.isEnabled = true
                binding.nextStepButton.isEnabled = true

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


    private fun setUpRestartButton() {
        binding.restartButton.isEnabled = false
        binding.restartButton.setOnClickListener {
            Log.d(Constants.TRBLE_FRAG, "restart button pressed")
            troubleAdapter.restart()
        }
    }

    private fun setUpBackStepButton() {
        binding.backStepButton.isEnabled = false
        binding.backStepButton.setOnClickListener {
            Log.d(Constants.TRBLE_FRAG, "back step button pressed")
            troubleAdapter.backStep()
        }
    }
    private fun setUpNextStepButton() {
        binding.nextStepButton.isEnabled = false
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
        binding.viewDiagnosisList.visibility = View.GONE
        binding.viewDiagnosisList.isEnabled = false
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
