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
            return troubleList.get(selectedIndex)
        }
        return null
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