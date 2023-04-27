package com.example.carcompanion.ui.find_help

import com.jillesvangurp.geojson.FeatureCollection
import com.jillesvangurp.overpasskotlinclient.OverpassClient
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import org.junit.Test
import org.osmdroid.util.GeoPoint
import java.io.File

class AutoShopSearcherTest {

    fun getFeatures(): FeatureCollection {
        // load json file searcherData.json
        val jsonText = File("src/test/java/com/example/carcompanion/ui/find_help/searcherData.json").readText()

        // parse json file
        val json = Json { ignoreUnknownKeys = true }
        val jsonObject = json.parseToJsonElement(jsonText).jsonObject

        return FeatureCollection.serializer().let {
            json.decodeFromJsonElement(it, jsonObject)
        }
    }

    @Test
    fun test_getAutoShops() = runBlocking {
        val client = mockk<OverpassClient>()
        coEvery { client.getGeoJson(any()) } returns getFeatures()

        val searcher = AutoShopSearcher(client)
        val features = searcher.getAutoShops(GeoPoint(0.0, 0.0), 100.0)

        coVerify { client.getGeoJson(any()) }

        assert(features.size == 7)
    }
}