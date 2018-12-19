package com.elyeproj.bottombarfragmentsswitching

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                createFragment("Home", "#FFFF00")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                createFragment("Dashboard", "#FF00FF")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                createFragment("Notifications", "#00FFFF")
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach { fragment ->
            if (fragment != null && fragment.isVisible) {
                with(fragment.childFragmentManager) {
                    if (backStackEntryCount > 0) {
                        popBackStack()
                        return
                    }
                }
            }
        }
        super.onBackPressed()
    }

    private fun createFragment(key: String, color: String) {
        if (supportFragmentManager.findFragmentByTag(key) != null) return
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_fragment, ContainerFragment.newInstance(key, color), key)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
