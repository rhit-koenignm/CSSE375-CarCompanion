package com.example.carcompanion

import com.example.carcompanion.database.CCDB
import com.example.carcompanion.ui.troubleshooting.Symptom
import com.example.carcompanion.ui.troubleshooting.TroubleData
import com.example.carcompanion.ui.troubleshooting.TroubleTreeUtils
import com.example.carcompanion.ui.troubleshooting.TroubleshootingFragment
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
    fun dbTest_loadTroubleData_size(){
        var db = CCDB()
        var troubleData = db.loadTroubleData()
        assertEquals(troubleData.size,10)
    }
    @Test
    fun dbTest_loadTroubleData_content(){
        var db = CCDB()
        var troubleData = db.loadTroubleData()
        assertEquals(troubleData.get(0).getTitle(),"Burning smell")
        assertEquals(troubleData.get(0).getText(),"Your car is producing a burning smell")
    }

    @Test
    fun dbTest_loadIndicatorTroubleData_size(){
        var db = CCDB()
        var indicatorTroubleData = db.loadIndicatorTroubleData()
        assertEquals(indicatorTroubleData.size,5)
    }
    @Test
    fun dbTest_loadIndicatorTroubleData_content(){
        var db = CCDB()
        var indicatorTroubleData = db.loadIndicatorTroubleData()
        assertEquals(indicatorTroubleData.get(1).getTitle(),"Weird Noise")
        assertEquals(indicatorTroubleData.get(1).getText(),"Is your car making a strange sound?")
    }

    @Test
    fun dbTest_loadDiagnosesTroubleData_size(){
        var db = CCDB()
        var troubles = db.loadDiagnosesTroubleData()
        assertEquals(troubles.size,6)
    }

    @Test
    fun dbTest_loadDiagnosesTroubleData_content(){
        var db = CCDB()
        var troubles = db.loadDiagnosesTroubleData()
        assertEquals(troubles.get(1).getTitle(),"Thermostat Failure")
        assertEquals(troubles.get(1).getText(),"It's small, it's inexpensive, but it plays a really important role in your vehicle's all-around makeup, especially its engine. For instance, when your thermostat fails, your engine won't work as well as it should. This is largely because these thermostats allow coolant to flow through the greater coolant system. A faulty thermostat could spell bigger engine issues down the road. Good news though: a new thermostat is inexpensive and can be installed fairly easily in less than an hour. How do you know whether or not your thermostat is bad? It's easy to test. Just start up your engine and put your hand on the radiator or its top hose. If it quickly warms up after a moment or two, it's working well. If it warms gradually from the start or doesn't warm up, you should look into thermostat replacement.\n")
    }
}