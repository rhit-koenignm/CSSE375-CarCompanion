package com.example.carcompanion

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.example.carcompanion.ui.car_info.CarDetails

class AddCarFragment(val user: String) : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_car, container, false)
        view.findViewById<Button>(R.id.btn_add_car).setOnClickListener { onAddCar(it) }
        return view
    }

    private fun onAddCar(view: View) {
        val carDocRef = FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(user)
            .collection("cars")

        val car = getCarDetails(view)
        carDocRef.add(car)
    }

    private fun getCarDetails(view: View): CarDetails {
        val car = CarDetails(
            view.findViewById<EditText>(R.id.car_nickname_et).text.toString(),
            view.findViewById<EditText>(R.id.car_year_et).text.toString(),
            view.findViewById<EditText>(R.id.car_make_et).text.toString(),
            view.findViewById<EditText>(R.id.car_model_et).text.toString(),
        )
        return car
    }
}