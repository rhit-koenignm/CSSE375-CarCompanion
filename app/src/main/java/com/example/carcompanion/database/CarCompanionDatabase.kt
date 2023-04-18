package com.example.carcompanion.database

import com.example.carcompanion.ui.troubleshooting.TroubleData

interface CarCompanionDatabase {
    fun loadTroubleDouble(): ArrayList<TroubleData>
    fun loadIndicatorTroubleData(): ArrayList<TroubleData>

    fun loadDiagnosesTroubleData(): ArrayList<TroubleData>
}