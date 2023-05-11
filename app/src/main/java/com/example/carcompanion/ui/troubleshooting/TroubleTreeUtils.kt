package com.example.carcompanion.ui.troubleshooting

import com.example.carcompanion.database.CCDB

object TroubleTreeUtils {
    var db = CCDB()
    fun loadTroubleTree(): TroubleShootingTree{
        return createTree()
    }

    // This tree creation relies on my knowing which index all these are at
    // This method is probably best refactored to use an external data source
    // rather than hard coding it
    fun createTree(): TroubleShootingTree {
        val troubleTree = TroubleShootingTree()
        var indicList = createIndicators() as ArrayList<Indicator>
        var diagnosesList = loadDiagnoses() as ArrayList<Diagnosis>
        var sympList = createSymptoms() as ArrayList<Symptom>

        //Now I'm going to add all our woes to the tree
        troubleTree.addWoes(indicList as ArrayList<TroubleShootingTree.Woe>)
        troubleTree.addWoes(diagnosesList as ArrayList<TroubleShootingTree.Woe>)
        troubleTree.addWoes(sympList as ArrayList<TroubleShootingTree.Woe>)

        return troubleTree
    }
    fun createIndicators(): ArrayList<TroubleShootingTree.Woe> {
        val troubleData = getIndicatorTroubleData()
        return ArrayList(troubleData.map { Indicator(it) })
    }
    fun loadDiagnoses(): ArrayList<TroubleShootingTree.Woe> {
        val troubles = getDiagnosesTroubleData()
        return ArrayList(troubles.map { Diagnosis(it) })
    }
    fun loadWoes(): ArrayList<TroubleShootingTree.Woe> {
        val woeList = ArrayList<TroubleShootingTree.Woe>()
        woeList.addAll(createIndicators())
        woeList.addAll(loadDiagnoses())
        woeList.addAll(createSymptoms())
        return woeList
    }

    fun createSymptoms(): ArrayList<TroubleShootingTree.Woe> {
        var troubleData = getTroubleData()
        return addSymptomData(troubleData)
    }

    private fun addSymptomData(troubleData: ArrayList<TroubleData>):  ArrayList<TroubleShootingTree.Woe> {
        var symptoms = ArrayList<TroubleShootingTree.Woe>()
        for(trobdata in troubleData){
            val newSymptom = Symptom(trobdata)
            symptoms.add(newSymptom)
        }
        return symptoms
    }

    private fun getTroubleData(): ArrayList<TroubleData> {
        return ArrayList<TroubleData>()
    }


    private fun getIndicatorTroubleData(): ArrayList<TroubleData> {
        return ArrayList<TroubleData>()
    }


    private fun getDiagnosesTroubleData(): ArrayList<TroubleData> {
        return ArrayList<TroubleData>()
    }
}
