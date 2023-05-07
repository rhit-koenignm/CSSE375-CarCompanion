package com.example.carcompanion.ui.car_info.car_details

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carcompanion.database.models.CarObject
import com.example.carcompanion.databinding.FragmentCarListBinding

class CarDetailFragment : Fragment() {

    private lateinit var binding: FragmentCarListBinding
    private lateinit var carListAdapter: CarListAdapter

    // Replace this with the actual list of CarObject items fetched from Firebase Firestore
    private val carList = listOf(
        CarObject("My Prius", 2006, "Toyota", "Prius", "Red"),
        CarObject("My Camry", 2010, "Toyota", "Camry", "Blue")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCarListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the RecyclerView and its adapter
//        carListAdapter = CarListAdapter(carList)
//        binding.carListRecyclerView.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = carListAdapter
//        }
    }
}