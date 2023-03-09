package com.example.carcompanion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.example.carcompanion.ui.car_info.CarDetailFragment
import com.example.carcompanion.ui.find_help.HelpMapActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import edu.rosehulman.kaupaies.carcompanion.ui.troubleshooting.DiagnosisDetailsFragment
import edu.rosehulman.kaupaies.carcompanion.ui.troubleshooting.TroubleShootingTree
import edu.rosehulman.kaupaies.carcompanion.ui.troubleshooting.TroubleshootingFragment

class MainActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener,
    TroubleshootingFragment.OnTroubleSelectedListener {

    private var currentFragment:String = "home"
    val auth = FirebaseAuth.getInstance()
    lateinit var user: String
    private var isAnon = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        window.statusBarColor(ContextCompat.getColor(activity_main, R.color.white))

        user = intent.getStringExtra(WelcomeActivity.USER_UID).toString()

        user = intent.getStringExtra(WelcomeActivity.USER_UID).toString()
        isAnon = intent.getBooleanExtra(WelcomeActivity.IS_ANON.toString(), true)

        //passing in each menu id
        val bottomNavView: BottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavView.setOnNavigationItemSelectedListener(this)

        title = "CarCompanion"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var switchTo: Fragment? = null

        when (item.itemId) {
            R.id.navigation_car_detail -> {
                currentFragment = "car detail"
                if(isAnon) {
                    switchFrag(AnonFragment())
                    return true
                }
                switchFrag(CarDetailFragment(this))
            }
            R.id.navigation_troubleshooting -> {
                currentFragment = "troubleshooting"
                switchFrag(TroubleshootingFragment())
            }
            R.id.navigation_find_help -> {
                currentFragment = "find help"
                val i = Intent(this@MainActivity, HelpMapActivity::class.java)
                startActivity(i)
//                switchFrag(FindHelpFragment())
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                auth.signOut()
                true
            }
            R.id.action_add_car -> {
                if(isAnon) {
                    switchFrag(AnonFragment())
                    return true
                }
                switchFrag(AddCarFragment(user))
                true
            }
            else -> false
        }
    }

    fun switchFrag(f: Fragment): Boolean {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, f)
        while (supportFragmentManager.backStackEntryCount > 0){
            supportFragmentManager.popBackStackImmediate()
        }
        ft.commit()
        return true
    }

    override fun onTroubleSelected(woe: TroubleShootingTree.Woe) {

        Log.d(Constants.TAG, "Trouble Selected: ${woe.getTitle()}\n")
        Log.d(Constants.TAG, "Woe is of type ${woe.getType()}")

        if(woe.getType() == "Diagnosis"){
            val detailFragment = DiagnosisDetailsFragment.newInstance(woe.data)
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.fragment_container, detailFragment)
            ft.addToBackStack("detail")
            ft.commit()
        }
        else{

            //if a non diagnosis is selected then we want to let the adapter know
            //will create function later
        }
    }
}
