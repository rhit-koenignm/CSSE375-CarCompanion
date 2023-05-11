package com.example.carcompanion.database

import com.example.carcompanion.ui.troubleshooting.Diagnosis
import com.example.carcompanion.ui.troubleshooting.Indicator
import com.example.carcompanion.ui.troubleshooting.Symptom
import com.example.carcompanion.ui.troubleshooting.TroubleData
import io.ktor.util.*

class CCDB : CarCompanionDatabase {

    var symptoms = HashMap<String,Symptom>()
    var indicators = HashMap<String,Indicator>()
    var diagnoses = HashMap<String,Diagnosis>()

    init {
        loadAllTroubleData()
    }
     fun loadSymptoms(): HashMap<String,Symptom> {
        return listOf(
            Symptom(TroubleData(
                "symptom_burning_smell",
                "Burning smell",
                "Your car is producing a burning smell")),
            Symptom(TroubleData("symptom_check_engine","Check Engine Light", "The check engine light on your dashboard is lit up")),
            Symptom(TroubleData("symptom_smell_sulfur","Smell of sulfur", "Your car is producing a smell like sulfur or rotten eggs")),
            Symptom(TroubleData("symptom_struggling_accelerate","Struggling to Accelerate", "Your car is struggling to accelerate")),
            Symptom(TroubleData("symptom_misfire_engine","Misfiring Engine", "Your car's engine is misfiring")),
            Symptom(TroubleData("symptom_highRPM_low_acceleration","High RPM with low acceleration", "Your car is struggling to accelerate with high RPM")),
            Symptom(TroubleData("symptom_clunking_sound","Clunking Sound", "Your car is producing a clunking sound")),
            Symptom(TroubleData("symptom_squealing_sound","Squealing Sound", "Your car is producing a squealing sound on braking")),
            Symptom(TroubleData("symptom_dark_smoke","Dark Smoke", "Your car is producing dark smoke coming from the exhaust")),
            Symptom(TroubleData("symptom_pink_fluid_leaking","Pink fluid leaking", "Your car is leaking a pink fluid"))
        ).fold(HashMap<String, Symptom>()) { set, symptom ->
            set[symptom.data.getId()] = symptom
            return set
        }

    }

     fun loadIndicators(): HashMap<String,Indicator> {
        return listOf(
            Indicator(TroubleData("indicator_flashing_light",
                "Flashing Light",
                "There are a variety of warning lights on your dashboard, are any lit?"
            )),
            Indicator(TroubleData("indicator_weird_noise","Weird Noise", "Is your car making a strange sound?")),
            Indicator(TroubleData(
                "indicator_weird_smell",
                "Weird Smell",
                "Is there a strangle smell on the outside or inside of your car?"
            )),
            Indicator(TroubleData(
                "indicator_performance_issues",
                "Performance Issues",
                "Is your car having trouble starting or accelerating?"
            )),
            Indicator(TroubleData(
                "indicator_strange_thing",
                "I see something strange",
                "Is there smoke coming out of your car?"
            )),
            Indicator(TroubleData(
                "indicator_weird_noises",
                "My car is making weird noises",
                "oh no there are some weird noises! I wonder why"
            )),Indicator(TroubleData(
                "indicator_heater_ac",
                "The heater or AC is not working",
                "oh no It's hot/cold in here, HELP!"
            ))
        ).fold(HashMap<String, Indicator>()) { set, indicator ->
            set[indicator.data.getId()] = indicator
            return set
        }
    }

     fun loadDiagnoses(): HashMap<String,Diagnosis> {

        return listOf(
            Diagnosis(
                TroubleData(
                    "diagnosis_faulty_ignition_coil",
                    "Faulty Ignition Coil",
                    "Ignition coils work to transfer ignition voltage so that the spark plugs can fire and the engine can start. Some common signs and symptoms of faulty ignition coils include a hard starting car, a car that frequently misfires, and a car that experiences poor acceleration or loses power. Faulty ignition coils usually don't present an immediate safety issue, but it's important to have the problem resolved before further engine damage has a chance to occur."
                )),
            Diagnosis(
                TroubleData(
                    "diagnosis_thermostat_failure",
                    "Thermostat Failure",
                    "It's small, it's inexpensive, but it plays a really important role in your vehicle's all-around makeup, especially its engine. For instance, when your thermostat fails, your engine won't work as well as it should. This is largely because these thermostats allow coolant to flow through the greater coolant system. A faulty thermostat could spell bigger engine issues down the road. Good news though: a new thermostat is inexpensive and can be installed fairly easily in less than an hour. How do you know whether or not your thermostat is bad? It's easy to test. Just start up your engine and put your hand on the radiator or its top hose. If it quickly warms up after a moment or two, it's working well. If it warms gradually from the start or doesn't warm up, you should look into thermostat replacement.\n"
                )),
            Diagnosis(
                TroubleData(
                    "diagnosis_transmission_line",
                    "Cracked or Broken Transmission Line",
                    "When your transmission cooling lines leak, it means you have a serious problem in your vehicle. Along with your engine, the transmission is arguably the most important part of your vehicle. If either the engine or the transmission fails, you no longer have a drivable car. That's why getting the transmission cooling lines fixed when a problem arises is key to maintaining a well-functioning vehicle. The longer you let something like this go, the worse the problem will catch"
                )),
            Diagnosis(
                TroubleData(
                    "diagnosis_faulty_transmission",
                    "Faulty Transmission",
                    "The transmission in your vehicle has hundreds of interconnected parts that are always moving, rubbing, heating up and interacting with other internal and external components. Because there are so many parts in the transmission - and because each of those parts is continually exposed to friction and heat - it is natural that your transmission components will experience more wear and tear than other, simpler, mechanisms in your vehicle makeup. Manual transmission vehicles need transmission fluid to keep gears lubricated and to prevent grinding. Automatic transmission vehicles need transmission fluid to create the hydraulic pressure that actually powers movement within the transmission. Without the right amount of clean and debris-free transmission fluid, transmissions can overheat and essential gears can slip, surge, or become ground down, and - especially in the case of automatic transmissions - total vehicle failure can occur.\n"
                )),
            Diagnosis(
                TroubleData(
                    "diagnosis_brake_pads",
                    "Worn Down Brake Pads",
                    "So, can you drive with worn brake pads? Technically, yes. Should you? Absolutely not — especially, if they are excessively or unevenly worn. Having your brake pads changed when needed and brake system serviced regularly can help extend the life of individual brake system components, support brake safety, and even improve your overall driving experience!\n" +
                            "\n" +
                            "A simple brake inspection could help avoid further vehicle issues, yet an estimated 25% of drivers on the road are driving with worn brakes! "
                )),
            Diagnosis(
                TroubleData(
                    "diagnosis_bad_spark_plugs",
                    "Bad Spark Plugs",
                    "Spark plugs replacement cost differs heavily depending on what car model you have. Spark plugs cost 20\$-100\$ each depending on the model and spark plug type. Spark plugs replacement labor cost can be 20-100\$ dollar on a 4 cylinder car engine, but up to 1000\$ on a V8 engine or another engine if they are located badly.\n" +
                            "\n" +
                            "Consider that you have a normal spark plug, and the price is 10\$ each, and are running a V6 engine, the price of the spark plugs will be 60\$. The labor cost differs a lot in different car models. In some cars, you can replace the spark plugs within 10 minutes, and some do require a couple of hours to do the job. But the most common labor cost of a spark plug replacement is around 50-150\$ for most cars."
                ))
        ).fold(HashMap<String, Diagnosis>()) { set, diagnosis ->
            set[diagnosis.data.getId()] = diagnosis
            return set
        }
    }


     fun loadAllTroubleData() {

        var indicators = loadIndicators()
        var symptoms = loadSymptoms()
        var diagnoses = loadDiagnoses()

        //add symptoms under indicator
        indicators["indicator_flashing_light"]?.addSymptom("symptom_check_engine")
        indicators["indicator_flashing_light"]?.addSymptom("symptom_smell_sulfur")

        indicators["indicator_weird_noise"]?.addSymptom("symptom_misfire_engine")
        indicators["indicator_weird_noise"]?.addSymptom("symptom_clunking_sound")
        indicators["indicator_weird_noise"]?.addSymptom("symptom_squealing_sound")

        indicators["indicator_weird_smell"]?.addSymptom("symptom_burning_smell")
        indicators["indicator_weird_smell"]?.addSymptom("symptom_smell_sulfur")
        indicators["indicator_weird_smell"]?.addSymptom("symptom_pink_fluid_leaking")

        indicators["indicator_performance_issues"]?.addSymptom("symptom_dark_smoke")
        indicators["indicator_performance_issues"]?.addSymptom("symptom_struggling_accelerate")
        indicators["indicator_performance_issues"]?.addSymptom("symptom_highRPM_low_acceleration")

        indicators["indicator_strange_thing"]?.addSymptom("symptom_pink_fluid_leaking")
        indicators["indicator_strange_thing"]?.addSymptom("symptom_smell_sulfur")
        indicators["indicator_strange_thing"]?.addSymptom("symptom_burning_smell")
        indicators["indicator_strange_thing"]?.addSymptom("symptom_dark_smoke")

        indicators["indicator_heater_ac"]?.addSymptom("symptom_check_engine")
        indicators["indicator_heater_ac"]?.addSymptom("symptom_pink_fluid_leaking")
        //add diagnoses under symptoms
        symptoms["symptom_burning_smell"]?.addDiagnosis("diagnosis_thermostat_failure")
        symptoms["symptom_burning_smell"]?.addDiagnosis("diagnosis_faulty_transmission")

        symptoms["symptom_check_engine"]?.addDiagnosis("diagnosis_thermostat_failure")
        symptoms["symptom_check_engine"]?.addDiagnosis("diagnosis_faulty_transmission")


    }

    override fun getIndicator(id: String): Indicator {
        return indicators[id]!!
    }

    override fun getSymptom(id: String): Symptom {
        return symptoms[id]!!
    }

    override fun getDiagnosis(id: String): Diagnosis {
        return diagnoses[id]!!
    }

    override fun getIndicators(id: String): List<Indicator> {
        return indicators.values.toList()
    }

    override fun getIndicatorToSym(indicatorID: String): List<Symptom> {
        var symptomIDs = indicators[indicatorID]?.symptoms
        return symptoms.filterKeys { symptomIDs!!.contains(it)}.values.toList()
    }

    override fun getSymptoms(id: String): List<Symptom> {
        return symptoms.values.toList()
    }

    override fun getSymptomToDiag(symptomID: String): List<Diagnosis> {
        var diagnosisIDs = indicators[symptomID]?.symptoms
        return diagnoses.filterKeys { diagnosisIDs!!.contains(it)}.values.toList()
    }

    override fun getDiagnoses(id: String): List<Diagnosis> {
        return diagnoses.values.toList()
    }

}