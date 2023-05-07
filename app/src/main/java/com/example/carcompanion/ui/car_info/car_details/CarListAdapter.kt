package com.example.carcompanion.ui.car_info.car_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.carcompanion.R
import com.example.carcompanion.database.models.CarObject
import com.example.carcompanion.databinding.CardviewCarDetailBinding

class CarListAdapter(val fragment: CarListFragment, private val carList: List<CarObject>) :
    RecyclerView.Adapter<CarListAdapter.CarViewHolder>() {
    val model = ViewModelProvider(fragment.requireActivity()).get(CarListViewModel::class.java)

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
    fun getCarAt(pos: Int) = carList[pos]

    inner class CarViewHolder(private val binding: CardviewCarDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                // The code here will let us navigate to the edit page and then open the edit page
//                var currentCar = carList[adapterPosition]
//                fragment.navigateToEditPage(currentCar.id)
            }
        }

        fun bind(carObject: CarObject) {
            binding.carNickTxt.text = carObject.nickname
            binding.carYmmTxt.text = "${carObject.year} ${carObject.make} ${carObject.model}"
        }
    }
}