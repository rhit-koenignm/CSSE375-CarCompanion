package com.example.carcompanion.ui.car_info.car_details

import com.google.firebase.firestore.DocumentSnapshot

data class CarDetails
    (var nickname: String = "",
     var year: String = "",
     var make: String = "",
     var model: String = "",
     var VIN: String = "",
     var licenseState: String = "",
     var licensePlate: String = "",
//     var diagnosisList: ArrayList<TroubleData> = ArrayList<TroubleData>()
    ) {

    companion object {
        fun fromSnapshot(snapshot: DocumentSnapshot) : CarDetails {
            return snapshot.toObject(CarDetails::class.java)!!
        }
    }
}