package edu.rosehulman.kaupaies.carcompanion.ui.troubleshooting

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_view_trouble.view.*

class TroubleViewHolder(itemView: View, adapter: TroubleAdapter) : RecyclerView.ViewHolder(itemView) {
    private val titleTextView = itemView.trouble_name_text_view as TextView

    init {
        itemView.setOnClickListener {
            adapter.selectTroubleAt(adapterPosition)
        }
    }

    fun bind(woe: TroubleShootingTree.Woe){
        titleTextView.text = woe.getTitle()
    }
}