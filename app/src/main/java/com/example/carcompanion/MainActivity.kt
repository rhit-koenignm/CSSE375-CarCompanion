package com.example.carcompanion

import com.example.carcompanion.ui.car_info.car_details.CarListFragment
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.carcompanion.databinding.ActivityMainBinding
import com.example.carcompanion.ui.AddCarFragment
import com.example.carcompanion.ui.AnonFragment
import com.example.carcompanion.ui.car_info.car_details.CarSpecificDetailsFragment
import com.example.carcompanion.ui.find_help.FindHelpFragment
import com.example.carcompanion.ui.home.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.example.carcompanion.ui.troubleshooting.DiagnosisDetailsFragment
import com.example.carcompanion.ui.troubleshooting.TroubleShootingTree
import com.example.carcompanion.ui.troubleshooting.TroubleshootingFragment
import com.example.carcompanion.ui.user_auth.FrontPageFragment

class MainActivity : AppCompatActivity(), TroubleshootingFragment.OnTroubleSelectedListener {

    lateinit var binding: ActivityMainBinding

    lateinit var bottomNav: BottomNavigationView

    private var currentFragment: String = "home"
    val auth = FirebaseAuth.getInstance()
    lateinit var user: String
    private var isAnon = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // switchFrag(AnonFragment())
//         setContentView(R.layout.activity_main)

        bottomNav = binding.bottomNavView
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_car_detail -> {
                    currentFragment = "car detail"
                    // if(isAnon) {
                    //     switchFrag(AnonFragment())
                    //     true
                    // }
                    switchFrag(CarListFragment(user))
                }

                R.id.navigation_troubleshooting -> {
                    currentFragment = "troubleshooting"
                    switchFrag(TroubleshootingFragment())
                }

                R.id.navigation_find_help -> {
                    currentFragment = "find help"
                    switchFrag(FindHelpFragment())
                }
            }
            true
        }

        user = intent.getStringExtra(WelcomeActivity.USER_UID).toString()
        // addFrag(CarListFragment(user))
        addFrag(HomeFragment())
        isAnon = intent.getBooleanExtra(WelcomeActivity.IS_ANON.toString(), true)

        title = "CarCompanion"
        requestPermissions()
    }

    fun requestPermissions() {
        // if permissions aren't requested, request them
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), 1
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(Constants.DEFAULT_TAG, "option selected!")
        return when (item.itemId) {
            R.id.action_logout -> {
                auth.signOut()
                // TODO: This should be handled in an auth observer
                val inputIntent = Intent(this, WelcomeActivity::class.java)
                startActivity(inputIntent)
                true
            }

            R.id.action_add_car -> {
                // if(isAnon) {
                //     switchFrag(AnonFragment())
                //     return true
                // }
                switchFrag(AddCarFragment(user))
                true
            }

            else -> false
        }
    }

    fun addFrag(f: Fragment): Boolean {
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.fragment_container, f)
        ft.addToBackStack(null)
        ft.commit()
        return true
    }

    fun switchFrag(f: Fragment): Boolean {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, f)
        ft.commit()
        return true
    }

    override fun onTroubleSelected(woe: TroubleShootingTree.Woe) {

        Log.d(Constants.DEFAULT_TAG, "Trouble Selected: ${woe.getTitle()}\n")
        Log.d(Constants.DEFAULT_TAG, "Woe is of type ${woe.getType()}")

        if (woe.getType() == "Diagnosis") {
            val detailFragment = DiagnosisDetailsFragment.newInstance(woe.data)
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.fragment_container, detailFragment)
            ft.addToBackStack("detail")
            ft.commit()
        } else {

            //if a non diagnosis is selected then we want to let the adapter know
            //will create function later
        }
    }
}
