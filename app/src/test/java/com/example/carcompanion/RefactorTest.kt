package com.example.carcompanion

import com.example.carcompanion.ui.troubleshooting.Symptom
import com.example.carcompanion.ui.troubleshooting.TroubleData
import com.example.carcompanion.ui.troubleshooting.TroubleTreeUtils
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

internal class RefactorTest {
//Elon Milestone 2:
    @Test
    fun dataClassTest() {
        var troubleData =TroubleData("Burning smell", "Your car is producing a burning smell")
        var synptomList = ArrayList<Symptom>()
        var newSymptom = Symptom(troubleData)

        synptomList.add(newSymptom)
        assertEquals(synptomList.get(0).getTitle(),"Burning smell")
    }

    @Test
    fun longMethdTest() {

    }

    @Test
    fun speculativeGeneralityTest() {

    }
}