package com.example.carcompanion.ui.find_help

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.MapView
import com.example.carcompanion.Constants
import com.example.carcompanion.databinding.FragmentFindHelpBinding
import com.google.android.gms.location.*
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker


class FindHelpFragment: Fragment() {
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private lateinit var binding: FragmentFindHelpBinding
    private lateinit var mapView: MapView
    private lateinit var fLocClient: FusedLocationProviderClient

    private var curLoc: GeoPoint? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentFindHelpBinding.inflate(inflater, container, false)
        //we need this for our map to show up
        Log.d(Constants.DEFAULT_TAG, "opened find help map")

        // Initialize Map
        Configuration.getInstance().userAgentValue = "CarCompanion"
        mapView = binding.map
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)
        mapView.controller.setZoom(15.0)

        // Get current location
        initLocation()

        binding.searchButton.setOnClickListener {
            lifecycleScope.launch {
                onSearch()
            }
        }

        return binding.root
    }

    private fun initLocation(): Boolean {
        val context = requireContext()
        fLocClient = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }

        fLocClient.lastLocation.addOnSuccessListener { location ->
            // log
            Log.d(Constants.DEFAULT_TAG, "initial location: $location")
            if (location != null) {
                mapView.controller.setCenter(GeoPoint(location))
            }
        }
        startLocationUpdates()
        return false
    }

    private fun startLocationUpdates() {
        locationRequest = LocationRequest.create()
            .apply { //https://stackoverflow.com/questions/66489605/is-constructor-locationrequest-deprecated-in-google-maps-v2
                interval = 1000 //can be much higher
                fastestInterval = 500
                smallestDisplacement = 10f //10m
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                maxWaitTime = 1000
            }
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    // Update UI with location data
                    onLocationUpdate(GeoPoint(location)) //MY function
                }
            }
        }
        val context = requireContext()
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fLocClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun onLocationUpdate(location: GeoPoint) {
        Log.d(Constants.DEFAULT_TAG, "location: $location")
        curLoc = GeoPoint(location)
        mapView.controller.setCenter(curLoc)
    }

    private suspend fun onSearch() {
        val searcher = AutoShopSearcher()
        // Within 5 miles of current location
        val shops = searcher.getAutoShops(curLoc!!, 8046.72)
        val markers = shops.map {
            Log.d(Constants.DEFAULT_TAG, "shop: $it")
            val marker = Marker(mapView)
            marker.title = it.name
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            marker.icon = ContextCompat.getDrawable(requireContext(), com.example.carcompanion.R.drawable.car_companion_icon)
            marker.position = it.location

            marker
        }

        mapView.overlays.addAll(markers)
        mapView.invalidate()
    }
}