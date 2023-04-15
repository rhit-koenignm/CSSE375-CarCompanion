package com.example.carcompanion.database

import com.example.carcompanion.ui.troubleshooting.TroubleData

class CCDB : CarCompanionDatabase {
    override fun loadTroubleDouble(): ArrayList<TroubleData> {
        var tempData = ArrayList<TroubleData>()
            tempData.add(TroubleData("Burning smell", "Your car is producing a burning smell"))
            tempData.add(TroubleData("Check Engine Light", "The check engine light on your dashboard is lit up"))
            tempData.add(TroubleData("Smell of sulfur", "Your car is producing a smell like sulfur or rotten eggs"))
            tempData.add(TroubleData("Struggling to Accelerate", "Your car is struggling to accelerate"))
            tempData.add(TroubleData("Misfiring Engine", "Your car's engine is misfiring"))
            tempData.add(TroubleData("High RPM with low acceleration", "Your car is struggling to accelerate with high RPM"))
            tempData.add(TroubleData("Clunking Sound", "Your car is producing a clunking sound"))
            tempData.add(TroubleData("Squealing Sound", "Your car is producing a squealing sound on braking"))
            tempData.add(TroubleData("Dark Smoke", "Your car is producing dark smoke coming from the exhaust"))
            tempData.add(TroubleData("Pink fluid leaking", "Your car is leaking a pink fluid"))
        return tempData
    }
}