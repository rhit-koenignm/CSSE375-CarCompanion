package com.example.carcompanion.ui.troubleshooting

import com.example.carcompanion.database.CCDB
import com.example.carcompanion.database.CarCompanionDatabase


class TroubleshootingFlowController(val db: CarCompanionDatabase = CCDB()) {
    var state: State = State.Start
        private set

    sealed class State {
        object Start : State()
        class PrimarySelected(val primary: Indicator) : State()
        class SecondarySelected(val primary: Indicator, val secondary: Symptom) : State()
        class ViewDiagnosis(val primary: Indicator, val secondary: Symptom, val diagnosis: Diagnosis) : State()

    }

    sealed interface Event {
        object ResetEvent : Event
        class SelectPrimarySymptomEvent(val data: Indicator) : Event
        class SelectSecondarySymptomEvent(val data: Symptom) : Event
        class SelectDiagnosisEvent(val data: Diagnosis) : Event
        object BackEvent : Event
    }

    fun getIndicatorFromId(id: String) : Indicator {
        return db.getIndicator(id)
    }

    fun getSymptomFromId(id: String) : Symptom {
        return db.getSymptom(id)
    }

    fun getDiagnosisFromId(id: String) : Diagnosis {
        return db.getDiagnosis(id)
    }


    fun selectWoe(woe: TroubleShootingTree.Woe): State {
        val evt = when(woe) {
            is Indicator -> Event.SelectPrimarySymptomEvent(woe)
            is Symptom -> Event.SelectSecondarySymptomEvent(woe)
            is Diagnosis -> Event.SelectDiagnosisEvent(woe)
            else -> throw IllegalArgumentException("Invalid woe type")
        }

        processEvent(evt)

        return state
    }

    fun getWoes(): List<TroubleShootingTree.Woe> {
        val state = state
        return when (state) {
            is State.Start -> {
                db.getIndicators()
            }
            is State.PrimarySelected -> {
                db.getIndicatorToSym(state.primary.data.getId())
            }
            is State.SecondarySelected -> {
                db.getSymptomToDiag(state.secondary.data.getId())
            }
            is State.ViewDiagnosis -> {
                emptyList()
            }
        }
    }

    fun processEvent(event: Event) {
        state = reduceState(state, event)
    }

    private fun reduceState(state: State, event: Event): State {
        if (event is Event.ResetEvent) {
            return State.Start
        }

        return when (state) {
            is State.Start -> {
                when (event) {
                    is Event.SelectPrimarySymptomEvent -> {
                        State.PrimarySelected(event.data)
                    }
                    else -> {
                        state
                    }
                }
            }
            is State.PrimarySelected -> {
                when (event) {
                    is Event.SelectSecondarySymptomEvent -> {
                        State.SecondarySelected(state.primary, event.data)
                    }
                    is Event.BackEvent -> {
                        State.Start
                    }
                    else -> {
                        state
                    }
                }
            }
            is State.SecondarySelected -> {
                when (event) {
                    is Event.SelectDiagnosisEvent -> {
                        State.ViewDiagnosis(state.primary, state.secondary, event.data)
                    }
                    is Event.BackEvent -> {
                        State.PrimarySelected(state.primary)
                    }
                    else -> {
                        state
                    }
                }
            }
            is State.ViewDiagnosis -> {
                when (event) {
                    is Event.BackEvent -> {
                        State.SecondarySelected(state.primary, state.secondary)
                    }
                    else -> {
                        state
                    }
                }
            }
        }
    }
}