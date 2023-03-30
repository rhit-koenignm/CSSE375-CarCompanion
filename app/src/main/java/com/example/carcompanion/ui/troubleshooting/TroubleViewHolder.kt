package com.example.carcompanion.ui.troubleshooting

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.carcompanion.R
import org.w3c.dom.Text

class TroubleViewHolder(itemView: View, adapter: TroubleAdapter) : RecyclerView.ViewHolder(itemView) {
    //private val titleTextView = itemView.trouble_name_text_view as TextView

    private lateinit var titleTextView: TextView

    init {
        //titleTextView = adapter.
//        itemView.setOnClickListener {
//            adapter.selectTroubleAt(adapterPosition)
//        }
    }

    fun bind(woe: TroubleShootingTree.Woe){
        //titleTextView.text = woe.getTitle()
    }
}