package com.example.carcompanion.ui.troubleshooting

class Indicator(data: TroubleData) : TroubleShootingTree.Woe(data){
    var woeType: String = "Indicator"

    override fun getType(): String{
        return woeType
    }

    fun setType(givenType: String){
        woeType = givenType
    }
}