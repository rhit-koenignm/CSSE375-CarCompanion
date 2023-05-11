package com.example.carcompanion.database

import com.example.carcompanion.ui.troubleshooting.Diagnosis
import com.example.carcompanion.ui.troubleshooting.Indicator
import com.example.carcompanion.ui.troubleshooting.Symptom
import com.example.carcompanion.ui.troubleshooting.TroubleData

interface CarCompanionDatabase {
    fun loadSymptoms(): HashMap<String,Symptom>
    fun loadIndicators(): HashMap<String,Indicator>

    fun loadDiagnoses(): HashMap<String,Diagnosis>

    fun loadAllTroubleData()
}