package edu.rosehulman.kaupaies.carcompanion.ui.troubleshooting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.rosehulman.kaupaies.carcompanion.R
//import kotlinx.android.synthetic.main..view.*
import kotlinx.android.synthetic.main.fragment_diagnosis_details.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_TR = "trouble"

/**
 * A simple [Fragment] subclass.
 * Use the [DocDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DiagnosisDetailsFragment : Fragment() {

    private var trouble: TroubleData? = null

    companion object {
        @JvmStatic
        fun newInstance(trouble: TroubleData): DiagnosisDetailsFragment{
            val fragment = DiagnosisDetailsFragment()
            fragment.arguments = Bundle()
            fragment.requireArguments().putParcelable(ARG_TR, trouble)
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            trouble = arguments?.getParcelable(ARG_TR)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_diagnosis_details, container, false)
        view.fragment_diagnosis_details_title.text = trouble?.title
        view.fragment_diagnosis_detail_body.text = trouble?.text

        return view
    }

}