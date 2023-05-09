package com.example.carcompanion.ui.troubleshooting

import com.example.carcompanion.database.CCDB

class TroubleShootingAPI {
    var currentIndex = 0
    var db = CCDB.loadAllTroubleData();
    fun getList(id: Int, type: String) : ArrayList<TroubleData>{
        var array = ArrayList<TroubleData>()

        for(arr in db!!){
            if(arr[currentIndex].getId()==id){
                //TODO: add all possible option to the temp array
//                if(currentIndex<arr.size){
//                    array.add(arr[currentIndex+1])
//                }
//
            }
        }

        return array
    }
}