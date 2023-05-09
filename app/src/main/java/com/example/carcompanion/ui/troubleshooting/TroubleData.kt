package com.example.carcompanion.ui.troubleshooting

import android.os.Parcelable
//import kotlinx.android.parcel.Parcelize

//@Parcelize
 data class TroubleData(private var title: String, private var text: String, private var id: Int){
    fun getTitle(): String{
     return title
    }
  fun getId(): Int {
    return id;
  }
  fun getText(): String{
   return text
  }
  fun setTitle(title: String){
   this.title = title
  }
  fun getText(text: String){
   this.text = text
  }
 }
    //: Parcelable
