package com.example.carcompanion.ui.car_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.carcompanion.database.models.CarObject
import com.example.carcompanion.databinding.FragmentCarDetailsBinding

class CarSpecificDetailsFragment(private val car: CarObject): Fragment() {

    private lateinit var binding: FragmentCarDetailsBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarDetailsBinding.inflate(inflater, container, false)
        //val view = inflater.inflate(R.layout.fragment_car_specific_details, container, false)

//        view.findViewById<TextView>(R.id.car_specific_nickname).text = car.nickname
//        view.findViewById<TextView>(R.id.car_specific_ymm_txt).text = "${car.year} ${car.make} ${car.model}"
//        view.findViewById<TextView>(R.id.vin_txt).text = car.VIN
//        view.findViewById<TextView>(R.id.license_plate_txt).text = car.licensePlate
//        view.findViewById<TextView>(R.id.license_state_txt).text = car.licenseState

        //var diagnosisView = view.findViewById<RecyclerView>(R.id.diagnosis_recycler) as RecyclerView


//        val adapter = DiagnosisAdapter()
//        diagnosisView.adapter = adapter
//        diagnosisView.layoutManager = LinearLayoutManager(context)
//        diagnosisView.setHasFixedSize(true)

        return binding.root
    }

}