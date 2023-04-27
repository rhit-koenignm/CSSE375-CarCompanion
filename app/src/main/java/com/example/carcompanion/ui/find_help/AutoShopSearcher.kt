package com.example.carcompanion.ui.find_help

import android.util.Log
import com.example.carcompanion.Constants
import com.jillesvangurp.geojson.Geometry
import com.jillesvangurp.overpasskotlinclient.OverpassClient
import org.osmdroid.util.GeoPoint

class AutoShopSearcher {
    private var client: OverpassClient

    constructor() : this(OverpassClient())

    constructor(client: OverpassClient) {
        this.client = client
    }

    suspend fun getAutoShops(point: GeoPoint, radius: Double): List<AutoRepairShop> {
        val shopType = "car_repair"

        val query = """
            [out:json];
            (
              node["shop"="$shopType"]
                (around:$radius, ${point.latitude},${point.longitude});
              way["shop"="$shopType"]
                (around:$radius, ${point.latitude},${point.longitude});
              relation["shop"="$shopType"](around:$radius, ${point.latitude},${point.longitude});
            );
            out body;
            >;
            out body;
        """.trimIndent()

        val featureCollection = client.getGeoJson(query)

        return featureCollection.features.map {
            val name = it.properties?.get("name").toString()
            val pt = it.geometry as Geometry.Point
            val coords = GeoPoint(pt.coordinates!![1], pt.coordinates!![0])

            AutoRepairShop(name, coords)
        }
    }
}