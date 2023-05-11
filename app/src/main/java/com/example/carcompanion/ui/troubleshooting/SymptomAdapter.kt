package com.example.carcompanion.ui.troubleshooting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.carcompanion.databinding.RowViewSymptomBinding
import com.example.carcompanion.R

class SymptomAdapter(val fragment: TroubleshootingFragment, private val symptomList: List<TroubleShootingTree.Woe>) : RecyclerView.Adapter<SymptomAdapter.SymptomViewHolder>(){

    var selectedSymptom: TroubleShootingTree.Woe? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymptomViewHolder {
        val binding = RowViewSymptomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SymptomViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return symptomList.size
    }

    override fun onBindViewHolder(holder: SymptomViewHolder, position: Int) {
        val symptom = symptomList[position]
        holder.bind(symptom, position)
    }


    inner class SymptomViewHolder(private val binding: RowViewSymptomBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(symptom: TroubleShootingTree.Woe, index: Int) {
            if (index % 2 == 0) {
                binding.symptomCardView.setCardBackgroundColor(
                    ContextCompat.getColor(binding.root.context, R.color.dark_teal))
            }
            binding.symptomNameTextView.text = symptom.getTitle()
            binding.symptomRadioButton.isChecked = true
            binding.symptomCardView.setOnClickListener {
                var isSelected: Boolean = binding.symptomRadioButton.isChecked
                if(isSelected) {
                    binding.symptomRadioButton.isChecked = !isSelected
                    selectedSymptom = symptom
                }
            }
        }
    }
}