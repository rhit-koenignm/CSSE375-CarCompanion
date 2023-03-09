package com.example.carcompanion.ui.car_info

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.kaupaies.carcompanion.MainActivity
import edu.rosehulman.kaupaies.carcompanion.R
import kotlinx.android.synthetic.main.cardview_car_detail.view.*

class CarDetailViewHolder(itemView: View, main: MainActivity) : RecyclerView.ViewHolder(itemView) {
    private val nicknameTextView = itemView.car_nick_txt as TextView
    private val yearMakeModelTV = itemView.car_ymm_txt as TextView
    lateinit var car: CarDetails

    init {
        itemView.setOnClickListener {
            main.switchFrag(CarSpecificDetailsFragment(car))
        }
    }

    @SuppressLint("SetTextI18n")
    fun bind(carDetails: CarDetails) {
        car = carDetails
        nicknameTextView.text = carDetails.nickname
        yearMakeModelTV.text = "${carDetails.year} ${carDetails.make} ${carDetails.model}"
    }


}