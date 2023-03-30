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
//import kotlinx.android.synthetic.main.fragment_add_car.*

class AddCarFragment(val user: String) : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_car, container, false)
        view.findViewById<Button>(R.id.btn_add_car).setOnClickListener {
            val car = CarDetails(
                view.findViewById<EditText>(R.id.car_nickname_et).text.toString(),
                    view.findViewById<EditText>(R.id.car_year_et).text.toString(),
                    view.findViewById<EditText>(R.id.car_make_et).text.toString(),
                    view.findViewById<EditText>(R.id.car_model_et).text.toString(),
                    view.findViewById<EditText>(R.id.vin_txt).text.toString(),
                    view.findViewById<EditText>(R.id.license_state_et).text.toString(),
                    view.findViewById<EditText>(R.id.license_plate_et).text.toString()
            )


            val usersRef = FirebaseFirestore
                    .getInstance()
                    .collection("users")
                    .document(user)
            usersRef.get().addOnSuccessListener {
                val userDoc = hashMapOf(
                        "name" to "Mia"
                )
                usersRef.set(userDoc, SetOptions.merge())
                        .addOnSuccessListener {
                            Log.d(Constants.DEFAULT_TAG, "added successfully!")
                        }
                        .addOnFailureListener {
                            Log.d(Constants.DEFAULT_TAG, "something went wrong with adding user :(")
                        }
            }

            val carDocRef = FirebaseFirestore
                    .getInstance()
                    .collection("users")
                    .document(user)
                    .collection("cars")

            carDocRef.add(car)
        }
        return view
    }
}