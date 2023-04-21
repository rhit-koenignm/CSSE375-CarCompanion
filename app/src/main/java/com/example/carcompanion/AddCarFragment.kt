package com.example.carcompanion

import CarListFragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.carcompanion.database.models.CarObject
import com.google.firebase.firestore.FirebaseFirestore

class AddCarFragment(val user: String) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_car, container, false)
        view.findViewById<Button>(R.id.btn_add_car)
            .setOnClickListener {
                Log.d(Constants.DEFAULT_TAG, "Add new car!")
                onAddCar(it)
            }
        return view
    }

    private fun onAddCar(view: View) {
        val carDocRef = FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(user)
            .collection("cars")

        val car = getCarObject()
        carDocRef.add(car).addOnSuccessListener {
            // Pop the current fragment (AddCarFragment) from the back stack to return to the previous fragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CarListFragment(user))
                .commit()
        }
    }

    private fun getCarObject(): CarObject {
        val car = CarObject(
            requireView().findViewById<EditText>(R.id.car_nickname_et).text.toString(),
            requireView().findViewById<EditText>(R.id.car_year_et).text.toString().toInt(),
            requireView().findViewById<EditText>(R.id.car_make_et).text.toString(),
            requireView().findViewById<EditText>(R.id.car_model_et).text.toString(),
        )
        return car
    }
}