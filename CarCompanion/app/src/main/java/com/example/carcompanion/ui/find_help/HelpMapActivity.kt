package com.example.carcompanion.ui.find_help

//import com.google.android.libraries.places.api.model.Place
//import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
//import com.google.android.libraries.places.api.net.PlacesClient

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
//import com.google.android.gms.location.FusedLocationProviderClient
//import com.google.android.gms.location.LocationServices
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.Marker
//import com.google.android.gms.maps.model.MarkerOptions
//import com.google.android.libraries.maps.GoogleMap
//import com.google.android.libraries.maps.OnMapReadyCallback
//import com.google.android.libraries.maps.model.CameraPosition
import com.example.carcompanion.Constants
import com.example.carcompanion.MainActivity

class HelpMapActivity : AppCompatActivity()
//    OnMapReadyCallback, GoogleMap.OnMarkerClickListener
{

    // FIXME This class will need to be updated to meet current android binding standards for activity

//    private lateinit var map: GoogleMap
//    private lateinit var cameraPosition: CameraPosition
    private lateinit var lastLocation: Location

//    private lateinit var binding: Activity

    //The entry point to the Places api

//    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setContentView(R.layout.fragment_find_help)


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
//        mapFragment.getMapAsync(this)
        Log.d(Constants.TAG, "Help Map Activity launched")
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //handling our buttons
        //we only care about the back home button for now

//        var backHomeButton = findViewById<Button>(R.id.back_home_button)
//        backHomeButton.setOnClickListener {
//            val i = Intent(this@HelpMapActivity, MainActivity::class.java)
//            startActivity(i)
//        }
    }

    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private fun setUpMap() {
        if(ActivityCompat.checkSelfPermission(this,
            android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE)
        }

        //Enables the my location layer which draws a light blue dot on the user's location
//        map.isMyLocationEnabled = true

        //gives the most recent location currently available


//        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
//            if(location != null){
//                lastLocation = location
//                val currentLatLng = LatLng(location.latitude, location.longitude)
//                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
//            }
//        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
//    override fun onMapReady(googleMap: GoogleMap) {
//        map = googleMap
//
//        // Add a marker at Rose Hulman and move the camera
//        val myPlace = LatLng(39.4833, -87.3241)
////        map.addMarker(MarkerOptions().position(myPlace).title("Marker in Rose Hulman Insititute of Technology"))
////        map.moveCamera(CameraUpdateFactory.newLatLng(myPlace))
//        map.getUiSettings().setZoomControlsEnabled(true)
//        map.setOnMarkerClickListener(this)
//        setUpMap()
//    }

    //override fun onMarkerClick(p0: Marker?) = false
//    override fun onMarkerClick(p0: com.google.android.libraries.maps.model.Marker?): Boolean {
//        TODO("Not yet implemented")
//    }
}