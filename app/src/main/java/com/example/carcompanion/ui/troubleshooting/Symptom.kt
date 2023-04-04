package com.example.carcompanion.ui.troubleshooting

class Symptom(data: TroubleData) : TroubleShootingTree.Woe(data) {
    var diagnoses = ArrayList<TroubleShootingTree.Woe>()

    override fun getType(): String{
        return "Symptom"
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