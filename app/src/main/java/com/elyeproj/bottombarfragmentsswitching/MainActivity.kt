package com.elyeproj.bottombarfragmentsswitching

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.SparseArray
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var savedStateSparseArray = SparseArray<Fragment.SavedState>()
    private var currentSelectItemId = R.id.navigation_home
    companion object {
        const val SAVED_STATE_CONTAINER_KEY = "ContainerKey"
        const val SAVED_STATE_CURRENT_TAB_KEY = "CurrentTabKey"
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                swapFragments(item.itemId, "Home", "#FFFF00")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                swapFragments(item.itemId, "Dashboard", "#FF00FF")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                swapFragments(item.itemId, "Notifications", "#00FFFF")
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            savedStateSparseArray = savedInstanceState.getSparseParcelableArray(SAVED_STATE_CONTAINER_KEY)
            currentSelectItemId = savedInstanceState.getInt(SAVED_STATE_CURRENT_TAB_KEY)
        }
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSparseParcelableArray(SAVED_STATE_CONTAINER_KEY, savedStateSparseArray)
        outState?.putInt(SAVED_STATE_CURRENT_TAB_KEY, currentSelectItemId)
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

    private fun swapFragments(@IdRes actionId: Int, key: String, color: String) {
        if (supportFragmentManager.findFragmentByTag(key) == null) {
            savedFragmentState(actionId)
            createFragment(key, color, actionId)
        }
    }

    private fun createFragment(key: String, color: String, actionId: Int) {
        val fragment = ContainerFragment.newInstance(key, color)
        fragment.setInitialSavedState(savedStateSparseArray[actionId])
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_fragment, fragment, key)
            .commit()
    }

    private fun savedFragmentState(actionId: Int) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.container_fragment)
        if (currentFragment != null) {
            savedStateSparseArray.put(currentSelectItemId,
                supportFragmentManager.saveFragmentInstanceState(currentFragment)
            )
        }
        currentSelectItemId = actionId
    }
}
