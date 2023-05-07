package com.example.carcompanion.database.models

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude

data class CarObject(
    var nickname: String = "My Prius",
    var year: Int = 2006,
    var make: String = "Toyota",
    var model: String = "Prius",
    var color: String = "Red",
) {
    @get:Exclude
    var id = ""

    fun toMap(): Map<String, Any?> {
        return mapOf(
            kCarColor to this.color,
            kCarMake to this.make,
            kCarModel to this.model,
            kCarNickname to this.nickname,
            kCarYear to this.year
        )
    }
    companion object {
        const val COLLECTION_PATH = "cars"
        const val kCarColor: String = "color"
        const val kCarMake = "make"
        const val kCarModel = "model"
        const val kCarNickname = "nickname"
        const val kCarYear = "year"


        fun from(snapshot: DocumentSnapshot): CarObject {
            val carObject = snapshot.toObject(CarObject::class.java)!!
            carObject.id = snapshot.id
            return carObject
        }
    }
}
