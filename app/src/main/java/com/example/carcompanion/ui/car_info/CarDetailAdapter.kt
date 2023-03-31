package com.example.carcompanion.ui.car_info

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.example.carcompanion.MainActivity
import com.example.carcompanion.R

class CarDetailAdapter (var context: Context?, var main: MainActivity): RecyclerView.Adapter<CarDetailViewHolder>() {

    private var carList:ArrayList<CarDetails> = ArrayList<CarDetails>()

   private val detailRef = main.user.let {
       FirebaseFirestore
           .getInstance()
           .collection("users")
           .document(it)
           .collection("cars")
   }

    init {
        detailRef.addSnapshotListener { snapshot: QuerySnapshot?, exception: FirebaseFirestoreException? ->
            carList.clear()
            if(snapshot == null)
                return@addSnapshotListener

            carList.addAll(snapshot.map{ CarDetails.fromSnapshot(it) })
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_car_detail, parent, false)
        return CarDetailViewHolder(view, main)
    }

    override fun onBindViewHolder(holder: CarDetailViewHolder, position: Int) {
        holder.bind(carList[position])
    }

    override fun getItemCount(): Int = carList.size
}