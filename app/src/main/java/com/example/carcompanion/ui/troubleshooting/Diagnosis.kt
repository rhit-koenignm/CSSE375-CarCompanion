package com.example.carcompanion.ui.troubleshooting

class Diagnosis(data: TroubleData) : TroubleShootingTree.Woe(data) {
    override fun getType(): String{
        return "Diagnosis"
    }
}