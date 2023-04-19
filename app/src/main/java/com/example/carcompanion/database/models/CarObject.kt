package com.example.carcompanion.database.models

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude

data class CarObject(
    var name: String = "My Prius",
    var year: Int = 2006,
    var make: String = "Toyota",
    var model: String = "Prius",
    var color: String = "Red",
) {
    @get:Exclude
    var id = ""

    companion object {
        const val COLLECTION_PATH = "cars"

        fun from(snapshot: DocumentSnapshot): CarObject {
            val carObject = snapshot.toObject(CarObject::class.java)!!
            carObject.id = snapshot.id
            return carObject
        }
    }
}
