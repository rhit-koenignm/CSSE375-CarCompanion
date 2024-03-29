package com.example.carcompanion.ui.troubleshooting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
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

        updateButtonsFromState()
        updateTitlesFromState()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun updateTitlesFromState() {
        when(flowController.state) {
            is State.Start -> {
                binding.troubleshootingTitle.text = getString(R.string.having_car_troubles)
                binding.currentTroubleshootingTitle.text = getString(R.string.init_trouble_title)
            }
            is State.PrimarySelected -> {
                binding.troubleshootingTitle.text = getString(R.string.let_s_narrow_it_down)
                binding.currentTroubleshootingTitle.text = getString(R.string.what_other_symptoms)
            }
            is State.SecondarySelected -> {
                binding.troubleshootingTitle.text = getString(R.string.some_possibilities)
                binding.currentTroubleshootingTitle.text = getString(R.string.click_to_learn_more)
            }
            is State.ViewDiagnosis -> {
                binding.troubleshootingTitle.text = getString(R.string.having_car_troubles)
                binding.currentTroubleshootingTitle.text = getString(R.string.not_sure)
            }
        }
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


    fun updateButtonsFromState() {
        when(flowController.state) {
            is State.Start -> {
                toggleNextButton(false)
                toggleBackButton(false)
                toggleRestartButton(false)
            }
            is State.PrimarySelected -> {
                toggleNextButton(false)
                toggleBackButton(true)
                toggleRestartButton(true)
            }
            is State.SecondarySelected -> {
                toggleNextButton(false)
                toggleBackButton(true)
                toggleRestartButton(true)
            }
            is State.ViewDiagnosis -> {
                toggleNextButton(false)
                toggleBackButton(true)
                toggleRestartButton(true)
            }
        }
    }


    private fun setUpRestartButton() {
        toggleRestartButton(false)
        binding.restartButton.setOnClickListener {
            Log.d(Constants.TRBLE_FRAG, "restart button pressed")
            troubleAdapter.restart()
            moveToNextPage()
        }
    }

    private fun setUpBackStepButton() {
        toggleBackButton(false)
        binding.backStepButton.setOnClickListener {
            Log.d(Constants.TRBLE_FRAG, "back step button pressed")
            troubleAdapter.backStep()
            moveToNextPage()
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

    fun moveToDiagnosisPage(controller: TroubleshootingFlowController): Boolean {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, DiagnosisDetailsFragment(controller))
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
