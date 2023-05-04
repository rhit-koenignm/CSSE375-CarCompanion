package com.example.carcompanion.ui.car_info.car_details

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carcompanion.MainActivity
import com.example.carcompanion.R
import com.example.carcompanion.database.models.CarObject

class CarDetailViewHolder(itemView: View, main: MainActivity) : RecyclerView.ViewHolder(itemView) {

    private val nicknameTextView: TextView = itemView.findViewById(R.id.car_nick_txt)
    private val yearMakeModelTV: TextView = itemView.findViewById(R.id.car_ymm_txt)

    lateinit var car: CarObject

    init {
        itemView.setOnClickListener {
            main.switchFrag(CarSpecificDetailsFragment(car))
        }
    }

    @SuppressLint("SetTextI18n")
    fun bind(carDetails: CarObject) {
        car = carDetails
        nicknameTextView.text = carDetails.nickname
        yearMakeModelTV.text = "${carDetails.year} ${carDetails.make} ${carDetails.model}"
    }
}