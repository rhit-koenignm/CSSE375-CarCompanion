package com.example.carcompanion.ui.car_info.car_details

import androidx.lifecycle.ViewModel
import com.example.carcompanion.database.models.CarObject
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CarViewModel(carDocumentId: String): ViewModel() {
    var uid = Firebase.auth.uid!!

    lateinit var ref: DocumentReference

    init {
        ref = Firebase.firestore.collection("users")
            .document(uid)
            .collection(CarObject.COLLECTION_PATH)
            .document(carDocumentId)
    }


}