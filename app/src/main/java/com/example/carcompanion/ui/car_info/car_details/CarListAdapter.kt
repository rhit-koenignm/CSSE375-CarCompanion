package com.example.carcompanion.ui.car_info.car_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carcompanion.database.models.CarObject
import com.example.carcompanion.databinding.CardviewCarDetailBinding

class CarListAdapter(private val carList: List<CarObject>) :
    RecyclerView.Adapter<CarListAdapter.CarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding = CardviewCarDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val carObject = carList[position]
        holder.bind(carObject)
    }

    override fun getItemCount(): Int {
        return carList.size
    }

    inner class CarViewHolder(private val binding: CardviewCarDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(carObject: CarObject) {
            binding.carNickTxt.text = carObject.nickname
            binding.carYmmTxt.text = "${carObject.year} ${carObject.make} ${carObject.model}"
        }
    }
}