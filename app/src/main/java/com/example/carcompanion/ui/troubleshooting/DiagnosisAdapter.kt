package com.example.carcompanion.ui.troubleshooting
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.carcompanion.R
import com.example.carcompanion.databinding.RowViewDiagnosisBinding

class DiagnosisAdapter(val fragment: TroubleshootingFragment, private val diagnosisList: List<Diagnosis>) : RecyclerView.Adapter<DiagnosisAdapter.DiagnosisViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DiagnosisViewHolder {
        val binding = RowViewDiagnosisBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DiagnosisViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiagnosisViewHolder, position: Int) {
        val diagnosis = diagnosisList[position]
        holder.bind(diagnosis, position)
    }

    override fun getItemCount(): Int {
        return diagnosisList.size;
    }

    inner class DiagnosisViewHolder(private val binding: RowViewDiagnosisBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(diagnosis: Diagnosis, index: Int) {
            if(index % 2 == 0) {
                binding.diagnosisCardView.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.dark_teal))
            }
            binding.diagnosisNameTextView.text = diagnosis.getTitle()
            binding.diagnosisCardView.setOnClickListener {
                fragment.moveToDiagnosisPage(diagnosis)
            }
        }
    }
}