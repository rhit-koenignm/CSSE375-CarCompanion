package com.example.carcompanion.ui

import com.example.carcompanion.ui.car_info.car_details.CarListFragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.carcompanion.Constants
import com.example.carcompanion.R
import com.example.carcompanion.database.models.CarObject
import com.example.carcompanion.databinding.FragmentEditCarBinding
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class EditCarFragment(val carId: String, val userId: String) : Fragment() {

    lateinit var carDocRef: DocumentReference;
    var isRefInitialized: Boolean = false;
    lateinit var binding: FragmentEditCarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_car, container, false)
        binding = FragmentEditCarBinding.bind(view)

        Log.d(Constants.DETAILS_PATH, "Attempting to bind page with userId $userId and carId $carId")
        val car = getCarObjectFromFirebase(carId)

        if(car != null) {
            // Making sure our page matches
            binding.editPageTitle.text = "Edit Your Car"

            binding.carNicknameEt.setText(car.nickname)
            binding.carYearEt.setText(car.year)
            binding.carMakeEt.setText(car.make)
            binding.carModelEt.setText(car.model)
        }

        binding.btnSaveChanges.setOnClickListener {
            Log.d(Constants.DEFAULT_TAG, "Saving changes to the car!")
            onEditCar(binding.root)
        }

        return binding.root
    }

    private fun getCarObjectFromFirebase(carId: String): CarObject? {
        if(carId.isEmpty() || userId.isEmpty()) {
            return null
        }

        carDocRef = FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(userId)
            .collection("cars")
            .document(carId)

        isRefInitialized = true;


        var car: CarObject? = null;

        carDocRef.get()
            .addOnSuccessListener { document ->
                if(document != null) {
                    Log.d(Constants.FIREBASE_TAG, "DocumentSnapshot data: ${document.data}")
                    car = CarObject.from(document)
                    car!!.id = document.id
                } else {
                    Log.d(Constants.FIREBASE_TAG, "No car with that id exists")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(Constants.FIREBASE_TAG, "Get carObject failed with ", exception)
            }

        Log.d(Constants.FIREBASE_TAG, "CarObject has id ${car?.id}")
        return car
    }

    private fun onEditCar(view: View) {
        if(!isRefInitialized || carId.isEmpty()) {
            return onAddCar(view)
        }

        var car = getCarObject()

        carDocRef.set(car.toMap())
            .addOnSuccessListener {
                Log.d(Constants.FIREBASE_TAG, "Successfully updated user ${userId}'s car with id: $carId")
            }
            .addOnFailureListener {
                Log.d(Constants.FIREBASE_TAG, "Failed to update user ${userId}'s car with id: $carId")
            }
    }

    private fun onAddCar(view: View) {
        val carDocRef = FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(userId)
            .collection("cars")

        val car = getCarObject()
        carDocRef.add(car).addOnSuccessListener {
            // Pop the current fragment (AddCarFragment) from the back stack to return to the previous fragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CarListFragment(userId))
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