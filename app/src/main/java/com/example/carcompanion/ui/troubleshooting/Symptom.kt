package com.example.carcompanion.ui.troubleshooting

class Symptom(data: TroubleData) : TroubleShootingTree.Woe(data) {
    var woeType: String = "Symptom"

    var diagnoses = ArrayList<TroubleShootingTree.Woe>()

    override fun getType(): String{
        return woeType
    }

    fun setType(givenType: String){
        woeType = givenType
    }

    fun addDiagnosis(diag: Diagnosis){
        diagnoses.add(diag)
    }

    @JvmName("getDiagnoses1")
    fun getDiagnoses(): ArrayList<TroubleShootingTree.Woe>{
        return diagnoses;
    }

    fun getDiagnosisAt(index: Int): TroubleShootingTree.Woe {
        return diagnoses[index]
    }
}