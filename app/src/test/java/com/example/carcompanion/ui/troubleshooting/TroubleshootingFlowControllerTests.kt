package com.example.carcompanion.ui.troubleshooting

import com.example.carcompanion.ui.troubleshooting.TroubleshootingFlowController.Event
import com.example.carcompanion.ui.troubleshooting.TroubleshootingFlowController.State
import junit.framework.TestCase.assertEquals
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

import org.hamcrest.CoreMatchers.instanceOf


class TroubleshootingFlowControllerTests {

    fun getIndicator() =
        Indicator(TroubleData("Test Indicator", "Test Indicator Description", "Test Indicator Fix"))

    fun getSymptom() =
        Symptom(TroubleData("Test Symptom", "Test Symptom Description", "Test Symptom Fix"))

    fun getDiagnosis() =
        Diagnosis(TroubleData("Test Diagnosis", "Test Diagnosis Description", "Test Diagnosis Fix"))

    @Test
    fun testBasicReset() {
        val controller = TroubleshootingFlowController()
        controller.processEvent(Event.ResetEvent)

        assertThat(controller.state, instanceOf(State.Start::class.java))
    }

    @Test
    fun testSelectPrimarySymptom() {
        val controller = TroubleshootingFlowController()
        val indicator = getIndicator()
        controller.processEvent(Event.SelectPrimarySymptomEvent(indicator))

        assertThat(controller.state, instanceOf(State.PrimarySelected::class.java))
        assertThat((controller.state as State.PrimarySelected).primary, instanceOf(Indicator::class.java))
    }

    @Test
    fun testSelectSecondarySymptom() {
        val controller = TroubleshootingFlowController()
        val indicator = getIndicator()
        val symptom = getSymptom()
        controller.processEvent(Event.SelectPrimarySymptomEvent(indicator))
        controller.processEvent(Event.SelectSecondarySymptomEvent(symptom))

        assertThat(controller.state, instanceOf(State.SecondarySelected::class.java))
        assertThat((controller.state as State.SecondarySelected).primary, instanceOf(Indicator::class.java))
        assertThat((controller.state as State.SecondarySelected).secondary, instanceOf(Symptom::class.java))

        assertEquals((controller.state as State.SecondarySelected).primary, indicator)
    }

    @Test
    fun testSelectDiagnosis() {
        val controller = TroubleshootingFlowController()
        val indicator = getIndicator()
        val symptom = getSymptom()
        val diagnosis = getDiagnosis()
        controller.processEvent(Event.SelectPrimarySymptomEvent(indicator))
        controller.processEvent(Event.SelectSecondarySymptomEvent(symptom))
        controller.processEvent(Event.SelectDiagnosisEvent(diagnosis))

        assertThat(controller.state, instanceOf(State.ViewDiagnosis::class.java))
        assertThat((controller.state as State.ViewDiagnosis).primary, instanceOf(Indicator::class.java))
        assertThat((controller.state as State.ViewDiagnosis).secondary, instanceOf(Symptom::class.java))
        assertThat((controller.state as State.ViewDiagnosis).diagnosis, instanceOf(Diagnosis::class.java))

        assertEquals((controller.state as State.ViewDiagnosis).primary, indicator)
    }

    @Test
    fun testSelectDiagnosisEarly() {
        val controller = TroubleshootingFlowController()
        val indicator = getIndicator()
        val diagnosis = getDiagnosis()
        controller.processEvent(Event.SelectPrimarySymptomEvent(indicator))
        controller.processEvent(Event.SelectDiagnosisEvent(diagnosis))

        assertThat(controller.state, instanceOf(State.PrimarySelected::class.java))
        assertThat((controller.state as State.PrimarySelected).primary, instanceOf(Indicator::class.java))

        assertEquals((controller.state as State.PrimarySelected).primary, indicator)
    }

    @Test
    fun testSelectSecondaryEarly() {
        val controller = TroubleshootingFlowController()
        val symptom = getSymptom()
        controller.processEvent(Event.SelectSecondarySymptomEvent(symptom))

        assertThat(controller.state, instanceOf(State.Start::class.java))
    }

    @Test
    fun testDiagnosisReset() {
        val controller = TroubleshootingFlowController()
        val indicator = getIndicator()
        val symptom = getSymptom()
        val diagnosis = getDiagnosis()
        controller.processEvent(Event.SelectPrimarySymptomEvent(indicator))
        controller.processEvent(Event.SelectSecondarySymptomEvent(symptom))
        controller.processEvent(Event.SelectDiagnosisEvent(diagnosis))
        controller.processEvent(Event.ResetEvent)

        assertThat(controller.state, instanceOf(State.Start::class.java))
    }
}