package com.example.carcompanion.ui.troubleshooting

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.carcompanion.Constants
import com.example.carcompanion.databinding.RowViewSymptomBinding
import com.example.carcompanion.R
import com.example.carcompanion.databinding.RowViewDiagnosisBinding
import com.example.carcompanion.databinding.RowViewTroubleBinding
import java.util.Locale

class TroubleAdapter(val fragment: TroubleshootingFragment) : RecyclerView.Adapter<TroubleAdapter.TroubleViewHolder>(){

    var currentState = "symptom" // Could also be "indicator
    var selectedSymptom: TroubleShootingTree.Woe? = null
    lateinit var troubleList: List<TroubleShootingTree.Woe>

    init {
        setUpForIndicators()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TroubleViewHolder {
        val binding = RowViewTroubleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TroubleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return troubleList.size
    }

    override fun onBindViewHolder(holder: TroubleViewHolder, position: Int) {
        val symptom = troubleList[position]
        holder.bind(symptom, position)
    }

    fun changeState(state: String) {
        when(state){
            "indicators" -> {
                currentState = state
                setUpForIndicators()
                notifyDataSetChanged()
            }
            "symptoms" -> {
                currentState = state
                setUpForSymptoms()
                notifyDataSetChanged()
            }
            "diagnoses" -> {
                currentState = state
                setUpForDiagnoses()
                notifyDataSetChanged()
            }
            else -> {
                Log.d(Constants.TRBLE_ADPTER, "State invalid");
            }
        }
    }

    private fun setUpForSymptoms() {
        troubleList = TroubleTreeUtils.createSymptoms()
    }

    private fun setUpForIndicators() {
        troubleList = TroubleTreeUtils.createIndicators()
    }

    private fun setUpForDiagnoses() {
        troubleList = TroubleTreeUtils.loadDiagnoses()
    }

    inner class TroubleViewHolder(private val binding: RowViewTroubleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(trouble: TroubleShootingTree.Woe, index: Int) {
            if(currentState.equals("diagnoses")) {
                binding.troubleNameTextView.text = trouble.getTitle()

                binding.troubleRadioButton.visibility = View.GONE

                var diagnosis = trouble as Diagnosis
                if(!diagnosis.getType().equals("Diagnosis")) {
                    diagnosis = Diagnosis(trouble.data)
                }
                binding.troubleCardView.setOnClickListener {
                    fragment.moveToDiagnosisPage(diagnosis)
                }
            } else {
                if (index % 2 == 0) {
                    binding.troubleCardView.setCardBackgroundColor(
                        ContextCompat.getColor(binding.root.context, R.color.dark_teal))
                }
                binding.troubleImageButton.visibility = View.GONE

                binding.troubleNameTextView.text = trouble.getTitle()

                binding.troubleRadioButton.isChecked = true
                binding.troubleCardView.setOnClickListener {
                    var isSelected: Boolean = binding.troubleRadioButton.isChecked
                    if(isSelected) {
                        binding.troubleRadioButton.isChecked = !isSelected
                        selectedSymptom = trouble
                        notifyItemChanged(adapterPosition)
                    } else {
                        binding.troubleRadioButton.isChecked = !isSelected
                        selectedSymptom = null
                        notifyItemChanged(adapterPosition)
                    }
                }
            }
        }
    }
}