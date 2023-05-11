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

        fun getTitle(): String {
            return this.data.getTitle()
        }

        fun getText(): String{
            return this.data.getText()
        }

        open fun getType(): String {
            return "Woe"
        }
    }

}