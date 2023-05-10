package com.example.carcompanion.ui.troubleshooting

class Indicator(data: TroubleData) : TroubleShootingTree.Woe(data){

    var symptoms = ArrayList<String>()

    override fun getType(): String{
        return "Indicator"
    }
    fun addSymptom(id: String){
        symptoms.add(id)
    }
}