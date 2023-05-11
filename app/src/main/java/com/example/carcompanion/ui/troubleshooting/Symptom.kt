package com.example.carcompanion.ui.troubleshooting

class Symptom(data: TroubleData) : TroubleShootingTree.Woe(data) {
    var diagnoses = ArrayList<String>()

    override fun getType(): String{
        return "Symptom"
    }

    fun addDiagnosis(id: String){
        diagnoses.add(id)
    }

    @JvmName("getDiagnoses1")
    fun getDiagnoses(): ArrayList<String> {
        return diagnoses;
    }

    fun getDiagnosisAt(index: Int): String {
        return diagnoses[index]
    }

}