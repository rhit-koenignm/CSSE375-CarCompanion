package com.example.carcompanion.ui.troubleshooting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.carcompanion.R
import com.example.carcompanion.databinding.FragmentDiagnosisDetailsBinding

//import kotlinx.android.synthetic.main..view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_TR = "trouble"

/**
 * A simple [Fragment] subclass.
 * Use the [DocDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DiagnosisDetailsFragment(diagnosis: Diagnosis) : Fragment() { // We will also need to add the state machine to have a way to go back to the troubleshooting page

    private lateinit var binding: FragmentDiagnosisDetailsBinding
    private var diagnosis: Diagnosis = diagnosis

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
            addFrag(TroubleshootingFragment())
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