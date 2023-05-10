package com.example.carcompanion.ui.troubleshooting

import android.os.Parcelable
//import kotlinx.android.parcel.Parcelize

//@Parcelize
 data class TroubleData(private var id: String, private var title: String, private var text: String){
    fun getTitle(): String{
     return title
    }
  fun getId(): String {
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
