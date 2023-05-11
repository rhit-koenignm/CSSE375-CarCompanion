package com.example.carcompanion.database

import com.example.carcompanion.ui.troubleshooting.Diagnosis
import com.example.carcompanion.ui.troubleshooting.Indicator
import com.example.carcompanion.ui.troubleshooting.Symptom
import com.example.carcompanion.ui.troubleshooting.TroubleData

interface CarCompanionDatabase {
    fun getIndicator(id: String): Indicator
    fun getSymptom(id: String): Symptom
    fun getDiagnosis(id: String): Diagnosis

    fun getIndicators(id: String): List<Indicator>
    fun getIndicatorToSym(indicatorID: String): List<Symptom>
    fun getSymptoms(id: String): List<Symptom>
    fun getSymptomToDiag(symptomID: String): List<Diagnosis>
    fun getDiagnoses(id: String): List<Diagnosis>
}