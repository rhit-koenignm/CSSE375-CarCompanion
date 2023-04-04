package com.example.carcompanion.ui.troubleshooting

class Indicator(data: TroubleData) : TroubleShootingTree.Woe(data){
    override fun getType(): String{
        return "Indicator"
    }
}