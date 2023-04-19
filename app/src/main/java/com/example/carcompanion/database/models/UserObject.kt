package com.example.carcompanion.database.models

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude


data class UserObject(
    var userName: String = "WhatsWrongWithMyCar",
    var firstName: String = "tempFirstName",
    var lastName: String = "tempLastName",
    var email: String = "ohnomycar@gmail.com",
    var hasCompletedSetup: Boolean = false,
) {
    @get:Exclude
    var id = ""

    companion object {
        const val COLLECTION_PATH = "users"

        // These will be used when we grab the user's data
        const val CAR_COLLECTION_PATH = "cars"
        const val LOG_COLLECTION_PATH = "logs"

        fun from(snapshot: DocumentSnapshot): UserObject {
            var userData = snapshot.toObject(UserObject::class.java)!!
            userData.id = snapshot.id
            return userData
        }
    }
}