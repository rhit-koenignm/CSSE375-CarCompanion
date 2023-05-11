package com.example.carcompanion.ui.troubleshooting

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.carcompanion.Constants
import com.example.carcompanion.R
import com.example.carcompanion.databinding.RowViewTroubleBinding

class TroubleAdapter(val fragment: TroubleshootingFragment) : RecyclerView.Adapter<TroubleAdapter.TroubleViewHolder>(){

    lateinit var currentState: String // Could also be "indicator
    var selectedIndex: Int = -1
    lateinit var troubleList: ArrayList<TroubleShootingTree.Woe>

    init {
        troubleList = ArrayList<TroubleShootingTree.Woe>()
        setUpForIndicators()
        currentState = "indicators"
        selectedIndex = -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TroubleViewHolder {
        val binding = RowViewTroubleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TroubleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return troubleList.size
    }

    override fun onBindViewHolder(holder: TroubleViewHolder, position: Int) {
        val symptom = troubleList.get(position)
        holder.bind(symptom, position)
    }

    fun getTroubleAt(index: Int) : TroubleShootingTree.Woe?{
        if (index < 0 || troubleList.size < index) {
            return troubleList.get(index)
        }
        return null
    }

    fun getSelectedTrouble() : TroubleShootingTree.Woe? {
        if (selectedIndex < 0 || troubleList.size < selectedIndex) {
            return troubleList[selectedIndex]
        }
        return null
    }

    fun changeState(state: String) {
        when(state){
            "indicators" -> {
                currentState = state
                setUpForIndicators()
            }
            "symptoms" -> {
                currentState = state
                setUpForSymptoms()
            }
            "diagnoses" -> {
                currentState = state
                setUpForDiagnoses()
            }
            else -> {
                Log.d(Constants.TRBLE_ADPTER, "State invalid");
            }
        }
    }

    private fun setUpForSymptoms() {
        selectedIndex = -1
        var size = troubleList.size
        troubleList.clear()
        notifyItemRangeRemoved(0, size)
        troubleList = TroubleTreeUtils.createSymptoms()
        notifyItemRangeInserted(0, troubleList.size)
    }

    private fun setUpForIndicators() {
        selectedIndex = -1
        if(troubleList.isEmpty()) {
            troubleList = TroubleTreeUtils.createIndicators()
            notifyItemRangeInserted(0, troubleList.size)
        } else {
            var size = troubleList.size
            troubleList.clear()
            notifyItemRangeRemoved(0, size)
            troubleList = TroubleTreeUtils.createIndicators()
            notifyItemRangeInserted(0, troubleList.size)
        }
    }

    private fun setUpForDiagnoses() {
        selectedIndex = -1
        var size = troubleList.size
        troubleList.clear()
        notifyItemRangeRemoved(0, size)
        troubleList = TroubleTreeUtils.loadDiagnoses()
        notifyItemRangeInserted(0, troubleList.size)
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
                binding.troubleImageButton.visibility = View.GONE

                binding.troubleNameTextView.text = trouble.getTitle()
                if(selectedIndex != index) {
                    binding.troubleViewLayout.setBackgroundColor(
                        ContextCompat.getColor(binding.root.context, R.color.seafoam))
                    binding.troubleRadioButton.isChecked = false
                }

                binding.troubleCardView.setOnClickListener {
                    if(selectedIndex != index) {
                        binding.troubleViewLayout.setBackgroundColor(
                            ContextCompat.getColor(binding.root.context, R.color.cerulean))
                        binding.troubleRadioButton.isChecked = true
                        var prevSelected = selectedIndex
                        if(prevSelected != -1) {
                            notifyItemChanged(prevSelected)
                        }
                        selectedIndex = index
                        Log.d(Constants.TRBLE_ADPTER, "Selected trouble is at index " + selectedIndex)
                    } else if(selectedIndex == index){
                        selectedIndex = -1
                        binding.troubleViewLayout.setBackgroundColor(
                            ContextCompat.getColor(binding.root.context, R.color.seafoam))
                        binding.troubleRadioButton.isChecked = false
                    }
                }
            }
        }
    }
}