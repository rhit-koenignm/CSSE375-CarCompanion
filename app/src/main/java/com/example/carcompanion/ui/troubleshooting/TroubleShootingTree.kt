@file:Suppress("UNCHECKED_CAST")

package com.example.carcompanion.ui.troubleshooting

import com.example.carcompanion.R

public class TroubleShootingTree {
    //Troubleshooting trees are composed of nodes that referred to as Woes
    //The top level woes at the top of the tree are inidicators and are the most vague form woes as they just help us to narrow down the symptom types
    var indicators = ArrayList<Woe>()
    var symptoms = ArrayList<Woe>()
    var diagnoses = ArrayList<Woe>()

    var currentStep: Int = 0
    var symptomPath = ArrayList<Woe>()

    //temporarily gonna put the troubles in an array list so I can display them and make sure the adapter is working
    var woes = ArrayList<Woe>()
    final var finalStep: Int = 20
    lateinit var currentWoe: Woe

    fun addWoes(list: java.util.ArrayList<Woe>){
        list.forEach {
            when (it) {
                is Indicator -> {
                    indicators.add(it)
                }
                is Diagnosis -> {
                    diagnoses.add(it)
                }
                is Symptom -> {
                    symptoms.add(it)
                }
                else -> {
                    // some type we have not yet handled
                }
            }
        }
    }

    fun startTroubleShooting(): ArrayList<Woe>{
        //so our currentStep is at 0 but we're going to reset it just in case
        currentStep = 0
        symptomPath = ArrayList<Woe>()

        //since this is the beginning, we want to return the indicators
        return indicators
    }

//    fun backStep(): ArrayList<Woe>{
//        currentStep
//    }
//
//    fun manualNextStep(): ArrayList<Woe> {
//
//    }

    fun nextStep(selected: Woe): ArrayList<Woe> {
        currentWoe = selected

        if(currentWoe.getType().equals("Symptom")){
            //Adding to the symptom path
            symptomPath.add(currentWoe)
        }

        if(currentWoe.symptoms.isEmpty()){
            //If there are no sub-symptoms, we're going to check if there are any common diagnosis
            if(symptomPath.size < 4){
                //Not enough relevant diagnoses, so we are going to look at another indicator path
                currentStep++
                return indicators[this.currentStep].symptoms
            }
            else {
                //We've found enough in common, so we can just return the common diagnoses
                currentStep = finalStep
                return getCommonDiagnoses()
            }
        }

        //If we make it down here, this symptom DOES have sub-symptoms so we should display those
        return currentWoe.symptoms
    }

    fun getStepTitle(): String {
        if(currentStep == 0){
            return R.string.init_trouble_title.toString()
        }
        else if(currentStep < finalStep){
            return currentWoe.getTitle()
        }
        else {
            return R.string.diagnosis_title.toString()
        }
    }

    fun getCommonDiagnoses(): ArrayList<Woe> {
        val commonDiagnoses = (symptomPath.get(0) as Symptom).diagnoses
        var doesContain = true
        var diagnosisMap = HashMap<Woe, Int>()
        //Instead of going through each arraylist and comparing with each other, I'm going to go through each and insert the diagnoses into a map and then grab the
        var symptom: Symptom
        var diagnoses: ArrayList<Diagnosis>

        for(i in 1 until symptomPath.size){
            symptom = symptomPath.get(i) as Symptom
            diagnoses = symptom.getDiagnoses() as ArrayList<Diagnosis>
            for(j in 1 until symptom.getDiagnoses().size){
                if(diagnosisMap.containsKey(diagnoses[j])){
                    var count = diagnosisMap.getValue(diagnoses[j]) + 1
                    diagnosisMap.put(diagnoses[j], count)
                }
                else {
                    diagnosisMap.put(diagnoses[j], 1)
                }
            }
        }

        for(Woe in diagnosisMap.keys){
            if(diagnosisMap.get(Woe)!! > 3){
                commonDiagnoses.add(Woe)
            }
        }

        return commonDiagnoses as ArrayList<Woe>
    }


    //This is a data class so I can find commonDiagnoses
    internal class indexArray(val arrayIndex: Int, val elementIndex: Int)


    //This section of the code is the actual classes that server as our "nodes"
    open class Woe(val data: TroubleData) : Comparable<Woe> {
        override fun compareTo(other: Woe): Int {
            if(data.equals(other.data)){
                return 0
            }
            else {
                return 1
            }
        }
        var symptoms = ArrayList<Woe>()

        fun getTitle(): String {
            return this.data.title
        }

        fun getText(): String{
            return this.data.text
        }

        open fun getType(): String {
            return "Woe"
        }

        fun addSymptom(symp: Symptom){
            symptoms.add(symp)
        }

        fun addSymptoms(symps: ArrayList<Symptom>){
            symptoms.addAll(symps)
        }
    }

}