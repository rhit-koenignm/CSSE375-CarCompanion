package com.example.carcompanion.ui.troubleshooting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.carcompanion.R
import com.example.carcompanion.databinding.FragmentDiagnosisDetailsBinding

import com.example.carcompanion.ui.troubleshooting.TroubleshootingFlowController.State
import com.example.carcompanion.ui.troubleshooting.TroubleshootingFlowController.Event
import kotlinx.coroutines.flow.flow

//import kotlinx.android.synthetic.main..view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_TR = "trouble"

/**
 * A simple [Fragment] subclass.
 * Use the [DocDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DiagnosisDetailsFragment(var flowController: TroubleshootingFlowController) : Fragment() { // We will also need to add the state machine to have a way to go back to the troubleshooting page

    private lateinit var binding: FragmentDiagnosisDetailsBinding
    private lateinit var diagnosis: Diagnosis

    init {
        when(flowController.state) {
            is State.ViewDiagnosis -> {
                diagnosis = (flowController.state as State.ViewDiagnosis).diagnosis
            }
            else -> {
                // In this case, the controller isn't in the proper state, so send it back to the
                // main troubleshooting page and don't touch the state
                addFrag(TroubleshootingFragment(flowController))
            }
        }
        diagnosis
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiagnosisDetailsBinding.inflate(inflater, container, false)

        binding.fragmentDiagnosisDetailsTitle.text =  diagnosis.getTitle()
        binding.fragmentDiagnosisDetailBody.text = diagnosis.getText()

        setupBackButton()
        return binding.root
    }

    fun setupBackButton() {
        binding.backToTroubleshootingButton.setOnClickListener {
            flowController.processEvent(Event.BackEvent)
            addFrag(TroubleshootingFragment(flowController))
        }
    }

    fun addFrag(f: Fragment) : Boolean {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, f)
            .addToBackStack(null)
            .commit()
        return true
    }
}