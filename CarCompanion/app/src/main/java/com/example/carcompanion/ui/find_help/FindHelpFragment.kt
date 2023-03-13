package com.example.carcompanion.ui.find_help

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.MapView
import com.example.carcompanion.Constants
import com.example.carcompanion.databinding.FragmentFindHelpBinding


class FindHelpFragment: Fragment() {
//    lateinit var myMapView: MapView
//    private lateinit var map: GoogleMap
    private lateinit var binding: FragmentFindHelpBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentFindHelpBinding.inflate(inflater, container, false)
        val root = binding.root
        //var v = inflater.inflate(R.layout.fragment_find_help, container, false)
//        myMapView = v.findViewById(R.id.map_view)
//        myMapView.onCreate(savedInstanceState)
//        myMapView.onResume()
        //we need this for our map to show up
        Log.d(Constants.TAG, "opened find help map")
        return root
    }

//    override fun onResume() {
//        super.onResume()
//        myMapView.onResume()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        myMapView.onPause()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        myMapView.onDestroy()
//    }
//
//    override fun onLowMemory() {
//        super.onLowMemory()
//        myMapView.onLowMemory()
//    }
//    override fun onMarkerClick(p0: Marker?) = false
//
//    override fun onMapReady(googleMap: GoogleMap) {
//        map = googleMap
//
//        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        map.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        map.moveCamera(CameraUpdateFactory.newLatLng(sydney))
//        map.getUiSettings().setZoomControlsEnabled(true)
//        map.setOnMarkerClickListener(this)
//    }
}