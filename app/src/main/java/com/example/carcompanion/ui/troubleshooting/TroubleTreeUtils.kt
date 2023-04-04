package com.example.carcompanion.ui.troubleshooting

object TroubleTreeUtils {

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

        //First I'm going to connect the indicators
        //This one is the flashing light indicator
        indicList.get(0).addSymptom(sympList[1] as Symptom)
        sympList[1].addSymptom(sympList[4])
        sympList[1].addDiagnosis(diagnosesList[3])

        //The second indicator is a weird noise
        indicList[1].addSymptom(sympList[6])
        sympList[6].addDiagnosis(diagnosesList[4])
        indicList[1].addSymptom(sympList[7])
        sympList[7].addDiagnosis(diagnosesList[5])


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
        var troubleData = addTroubleData()
        return addSyntomData(troubleData)
    }

    private fun addSyntomData(troubleData: ArrayList<TroubleData>):  ArrayList<TroubleShootingTree.Woe> {
        var symptoms = ArrayList<TroubleShootingTree.Woe>()
        for(trobdata in troubleData){
            val newSymptom = Symptom(trobdata)
            symptoms.add(newSymptom)
        }
        return symptoms
    }

    private fun addTroubleData(): ArrayList<TroubleData> {
        var tempData = ArrayList<TroubleData>()
        tempData.add(TroubleData("Burning smell", "Your car is producing a burning smell"))
        tempData.add(TroubleData("Check Engine Light", "The check engine light on your dashboard is lit up"))
        tempData.add(TroubleData("Smell of sulfur", "Your car is producing a smell like sulfur or rotten eggs"))
        tempData.add(TroubleData("Struggling to Accelerate", "Your car is struggling to accelerate"))
        tempData.add(TroubleData("Misfiring Engine", "Your car's engine is misfiring"))
        tempData.add(TroubleData("High RPM with low acceleration", "Your car is struggling to accelerate with high RPM"))
        tempData.add(TroubleData("Clunking Sound", "Your car is producing a clunking sound"))
        tempData.add(TroubleData("Squealing Sound", "Your car is producing a squealing sound on braking"))
        tempData.add(TroubleData("Dark Smoke", "Your car is producing dark smoke coming from the exhaust"))
        tempData.add(TroubleData("Pink fluid leaking", "Your car is leaking a pink fluid"))
        return tempData
    }


    private fun getIndicatorTroubleData(): ArrayList<TroubleData> {
        var troubleData = ArrayList<TroubleData>()
        troubleData.add(
            TroubleData(
                "Flashing Light",
                "There are a variety of warning lights on your dashboard, are any lit?"
            )
        )
        troubleData.add(TroubleData("Weird Noise", "Is your car making a strange sound?"))
        troubleData.add(
            TroubleData(
                "Weird Smell",
                "Is there a strangle smell on the outside or inside of your car?"
            )
        )
        troubleData.add(
            TroubleData(
                "Performance Issues",
                "Is your car having trouble starting or accelerating?"
            )
        )
        troubleData.add(
            TroubleData(
                "I see something strange",
                "Is there smoke coming out of your car?"
            )
        )
        return troubleData
    }


    private fun getDiagnosesTroubleData(): ArrayList<TroubleData> {
        val troubles = ArrayList<TroubleData>()
        troubles.add(
            TroubleData(
                "Faulty Ignition Coil",
                "Ignition coils work to transfer ignition voltage so that the spark plugs can fire and the engine can start. Some common signs and symptoms of faulty ignition coils include a hard starting car, a car that frequently misfires, and a car that experiences poor acceleration or loses power. Faulty ignition coils usually don't present an immediate safety issue, but it's important to have the problem resolved before further engine damage has a chance to occur."
            )
        )
        troubles.add(
            TroubleData(
                "Thermostat Failure",
                "It's small, it's inexpensive, but it plays a really important role in your vehicle's all-around makeup, especially its engine. For instance, when your thermostat fails, your engine won't work as well as it should. This is largely because these thermostats allow coolant to flow through the greater coolant system. A faulty thermostat could spell bigger engine issues down the road. Good news though: a new thermostat is inexpensive and can be installed fairly easily in less than an hour. How do you know whether or not your thermostat is bad? It's easy to test. Just start up your engine and put your hand on the radiator or its top hose. If it quickly warms up after a moment or two, it's working well. If it warms gradually from the start or doesn't warm up, you should look into thermostat replacement.\n"
            )
        )
        troubles.add(
            TroubleData(
                "Cracked or Broken Transmission Line",
                "When your transmission cooling lines leak, it means you have a serious problem in your vehicle. Along with your engine, the transmission is arguably the most important part of your vehicle. If either the engine or the transmission fails, you no longer have a drivable car. That's why getting the transmission cooling lines fixed when a problem arises is key to maintaining a well-functioning vehicle. The longer you let something like this go, the worse the problem will catch"
            )
        )
        troubles.add(
            TroubleData(
                "Faulty Transmission",
                "The transmission in your vehicle has hundreds of interconnected parts that are always moving, rubbing, heating up and interacting with other internal and external components. Because there are so many parts in the transmission - and because each of those parts is continually exposed to friction and heat - it is natural that your transmission components will experience more wear and tear than other, simpler, mechanisms in your vehicle makeup. Manual transmission vehicles need transmission fluid to keep gears lubricated and to prevent grinding. Automatic transmission vehicles need transmission fluid to create the hydraulic pressure that actually powers movement within the transmission. Without the right amount of clean and debris-free transmission fluid, transmissions can overheat and essential gears can slip, surge, or become ground down, and - especially in the case of automatic transmissions - total vehicle failure can occur.\n"
            )
        )
        troubles.add(
            TroubleData(
                "Worn Down Brake Pads",
                "So, can you drive with worn brake pads? Technically, yes. Should you? Absolutely not — especially, if they are excessively or unevenly worn. Having your brake pads changed when needed and brake system serviced regularly can help extend the life of individual brake system components, support brake safety, and even improve your overall driving experience!\n" +
                        "\n" +
                        "A simple brake inspection could help avoid further vehicle issues, yet an estimated 25% of drivers on the road are driving with worn brakes! "
            )
        )
        troubles.add(
            TroubleData(
                "Bad Spark Plugs",
                "Spark plugs replacement cost differs heavily depending on what car model you have. Spark plugs cost 20\$-100\$ each depending on the model and spark plug type. Spark plugs replacement labor cost can be 20-100\$ dollar on a 4 cylinder car engine, but up to 1000\$ on a V8 engine or another engine if they are located badly.\n" +
                        "\n" +
                        "Consider that you have a normal spark plug, and the price is 10\$ each, and are running a V6 engine, the price of the spark plugs will be 60\$. The labor cost differs a lot in different car models. In some cars, you can replace the spark plugs within 10 minutes, and some do require a couple of hours to do the job. But the most common labor cost of a spark plug replacement is around 50-150\$ for most cars."
            )
        )
        return troubles
    }
}
