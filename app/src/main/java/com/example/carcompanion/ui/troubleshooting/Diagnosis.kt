package com.example.carcompanion.ui.troubleshooting

class Diagnosis(data: TroubleData) : TroubleShootingTree.Woe(data) {
    var woeType: String = "Diagnosis"

    override fun getType(): String{
        return woeType
    }

    //made this function to make sure I can have each woe have the right type
    fun setType(givenType: String){
        woeType = givenType
    }
}