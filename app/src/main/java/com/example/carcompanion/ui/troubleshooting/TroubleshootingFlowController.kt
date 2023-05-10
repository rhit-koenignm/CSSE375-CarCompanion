package com.example.carcompanion.ui.troubleshooting

import kotlinx.coroutines.runBlocking
import ru.nsk.kstatemachine.DataEvent
import ru.nsk.kstatemachine.DefaultState
import ru.nsk.kstatemachine.Event
import ru.nsk.kstatemachine.StateMachine
import ru.nsk.kstatemachine.addInitialState
import ru.nsk.kstatemachine.createStateMachine
import ru.nsk.kstatemachine.transition

class TroubleshootingFlowController {
    sealed interface States {
        object Start : States
        class PrimarySelected(override val data: String) : DefaultState(), States
        object SecondarySelected : States
        object ViewDiagnosis : States
    }

    sealed interface Events {
        object ResetEvent : Event, Events
        class SelectPrimarySymptomEvent(override val data: Indicator) : DataEvent<Indicator>, Events
        class SelectSecondarySymptomEvent(override val data: Symptom) : DataEvent<Symptom>, Events
        class SelectDiagnosisEvent(override val data: Diagnosis) : DataEvent<Diagnosis>, Events
        object BackEvent : Event, Events
    }

    val machine: StateMachine = runBlocking {
        createStateMachine(this) {
            addInitialState(States.Start) {
                transition<Events.ResetEvent> {
                    targetState = States.Start
                }
            }
        }
    }

    val state: States
        get() = machine as States

    suspend fun nextScreen(woe: TroubleShootingTree.Woe) {
        // send event depending on type
        when (woe) {
            is Indicator -> {
                machine.processEvent(Events.SelectPrimarySymptomEvent(woe))
            }
            is Symptom -> {
                machine.processEvent(Events.SelectSecondarySymptomEvent(woe))
            }
            is Diagnosis -> {
                machine.processEvent(Events.SelectDiagnosisEvent(woe))
            }
        }
    }
}