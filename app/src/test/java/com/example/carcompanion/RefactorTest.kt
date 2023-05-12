package com.example.carcompanion

import com.example.carcompanion.database.CCDB
import com.example.carcompanion.ui.troubleshooting.Symptom
import com.example.carcompanion.ui.troubleshooting.TroubleData
import org.junit.Assert.assertEquals
import org.junit.Test

internal class RefactorTest {
//Elon Milestone 2:
    @Test
    fun dataClassTest() {
        var troubleData =TroubleData("symptom_burning_smell","Burning smell", "Your car is producing a burning smell")
        var symptomList = ArrayList<Symptom>()
        var newSymptom = Symptom(troubleData)

        symptomList.add(newSymptom)
        assertEquals(symptomList.get(0).getTitle(),"Burning smell")
    }

    @Test
    fun dbTest_getIndicators_size(){
        var db = CCDB()
        assertEquals(7,db.getIndicators().size)
    }
    @Test
    fun dbTest_getIndicator(){
        var db = CCDB()
        assertEquals("There are a variety of warning lights on your dashboard, are any lit?"
            ,db.getIndicator("indicator_flashing_light").getText())
    }
    @Test
    fun dbTest_getSymptoms_size(){
        var db = CCDB()
        assertEquals(13,db.getSymptoms().size)
    }
    @Test
    fun dbTest_getSymptom(){
        var db = CCDB()
        assertEquals("Your car is producing a burning smell"
            ,db.getSymptom("symptom_burning_smell").getText())
    }

    @Test
    fun dbTest_getDiagnoses_size(){
        var db = CCDB()
        assertEquals(10,db.getDiagnoses().size)
    }
    @Test
    fun dbTest_getDiagnosis(){
        var db = CCDB()
        assertEquals("Bad Spark Plugs"
            ,db.getDiagnosis("diagnosis_bad_spark_plugs").getTitle())
    }

    @Test
    fun dbTest_getIndicatorToSym_size(){
        var db = CCDB()
        assertEquals(3
            ,db.getIndicatorToSym("indicator_weird_noise").size)
    }
    @Test
    fun dbTest_getIndicatorToSym(){
        var db = CCDB()
        assertEquals("Squealing Sound"
            , db.getIndicatorToSym("indicator_weird_noise")[0].getTitle()
        )
        assertEquals("Misfiring Engine"
            , db.getIndicatorToSym("indicator_weird_noise")[1].getTitle()
        )
        assertEquals("Clunking Sound"
            , db.getIndicatorToSym("indicator_weird_noise")[2].getTitle()
        )
    }

    @Test
    fun dbTest_getSymptomToDiag_size(){
        var db = CCDB()
        assertEquals(2
            ,db.getSymptomToDiag("symptom_struggling_accelerate").size)
    }
    @Test
    fun dbTest_getSymptomToDiag(){
        var db = CCDB()
        assertEquals("Bad Spark Plugs"
            , db.getSymptomToDiag("symptom_struggling_accelerate")[0].getTitle()
        )
        assertEquals("Faulty Transmission"
            , db.getSymptomToDiag("symptom_struggling_accelerate")[1].getTitle()
        )
    }
}