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
import com.example.carcompanion.ui.troubleshooting.TroubleshootingFlowController.State
import com.example.carcompanion.ui.troubleshooting.TroubleshootingFlowController.Event

class TroubleAdapter(val fragment: TroubleshootingFragment, val flowController: TroubleshootingFlowController) : RecyclerView.Adapter<TroubleAdapter.TroubleViewHolder>(){

//    lateinit var currentState: String // Could also be "indicator
    var selectedIndex: Int = -1
    lateinit var troubleList: ArrayList<TroubleShootingTree.Woe>

    init {
        troubleList = ArrayList<TroubleShootingTree.Woe>()
        updateWoeList()
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

    fun getSelectedTroubleId() : String {
        var selectedWoe = getSelectedTrouble()
        if(selectedWoe == null) {
            return ""
        }
        return selectedWoe.data.getId()
    }

    fun getSelectedTrouble() : TroubleShootingTree.Woe? {
        if (selectedIndex < 0 || troubleList.size < selectedIndex) {
            return null
        }
        return troubleList.get(selectedIndex)
    }

    fun nextStep() : Boolean {
        if(selectedIndex != -1 || getSelectedTroubleId() == "") {
            when(flowController.state) {
                is State.Start -> {
                    try {
                        var selectedIndicator = flowController.getIndicatorFromId(getSelectedTroubleId())
                        if(selectedIndicator == null) {
                            return false
                        }
                        flowController.processEvent(Event.SelectPrimarySymptomEvent(selectedIndicator))
//                    updateWoeList()
                        return true
                    } catch (err: NoSuchElementException) {
                        Log.d(Constants.TRBLE_ADPTER, "No indicator for the ID " + getSelectedTroubleId())
                        return false
                    }
                }
                is State.PrimarySelected -> {
                    var selectedSymptom = flowController.getSymptomFromId(getSelectedTroubleId())
                    if(selectedSymptom == null) {
                        return false
                    }
                    flowController.processEvent(Event.SelectSecondarySymptomEvent(selectedSymptom))
//                    updateWoeList()
                    return true
                }
                is State.SecondarySelected -> {
                    var selectedDiagnosis = flowController.getDiagnosisFromId(getSelectedTroubleId())
                    if(selectedDiagnosis == null) {
                        return false
                    }
                    flowController.processEvent(Event.SelectDiagnosisEvent(selectedDiagnosis))
//                    updateWoeList()
                    return true
                }
                is State.ViewDiagnosis -> {
                    return false
                }
            }
        } else {
            return false
        }
    }

    fun restart() {
        flowController.processEvent(Event.ResetEvent)
        updateWoeList()
    }

    fun backStep() {
        flowController.processEvent(Event.BackEvent)
        updateWoeList()
    }

    private fun updateWoeList() {
        selectedIndex = -1
        if(troubleList.isEmpty()) {
            troubleList = ArrayList(flowController.getWoes())
            notifyItemRangeInserted(0, troubleList.size)
        } else {
            var size = troubleList.size
            troubleList.clear()
            notifyItemRangeRemoved(0, size)
            troubleList = ArrayList(flowController.getWoes())
            notifyItemRangeInserted(0, troubleList.size)
        }
    }


    inner class TroubleViewHolder(private val binding: RowViewTroubleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(trouble: TroubleShootingTree.Woe, index: Int) {
            Log.d(Constants.TRBLE_ADPTER, "These woes are type: " + trouble.getType())
            if(trouble.getType().equals("Diagnosis")) {
                binding.troubleNameTextView.text = trouble.getTitle()
                binding.troubleRadioButton.visibility = View.GONE

                binding.troubleCardView.setOnClickListener {
                    flowController.processEvent(Event.SelectDiagnosisEvent(getSelectedTrouble() as Diagnosis))
                    fragment.moveToDiagnosisPage(trouble as Diagnosis)
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
                        fragment.toggleNextButton(true)
                    } else if(selectedIndex == index){
                        selectedIndex = -1
                        Log.d(Constants.TRBLE_ADPTER, "Unselected trouble is at index " + selectedIndex)
                        binding.troubleViewLayout.setBackgroundColor(
                            ContextCompat.getColor(binding.root.context, R.color.seafoam))
                        binding.troubleRadioButton.isChecked = false
                        fragment.toggleNextButton(false)
                    }
                }
            }
        }
    }
}