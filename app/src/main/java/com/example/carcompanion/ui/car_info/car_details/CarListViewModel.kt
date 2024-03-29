package com.example.carcompanion.ui.car_info.car_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carcompanion.database.models.CarObject
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CarListViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    val carListLiveData: MutableLiveData<List<CarObject>> = MutableLiveData()

    fun fetchCarList(user: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val carList = mutableListOf<CarObject>()
            val querySnapshot = firestore.collection("users")
                .document(user)
                .collection(CarObject.COLLECTION_PATH)
                .get()
                .await()

            for (document in querySnapshot.documents) {
                val carObject = CarObject.from(document)
                carList.add(carObject)
            }

            carListLiveData.postValue(carList)
        }
    }
}