package com.elyeproj.bottombarfragmentsswitching

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.util.SparseArray
import com.elyeproj.bottombarfragmentsswitching.common.ContainerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView


class MainConventionalActivity : AppCompatActivity() {

    private var savedStateSparseArray = SparseArray<Fragment.SavedState>()
    private var currentSelectItemId = R.id.navigation_home
    companion object {
        const val SAVED_STATE_CONTAINER_KEY = "ContainerKey"
        const val SAVED_STATE_CURRENT_TAB_KEY = "CurrentTabKey"
    }

    private val mOnNavigationItemSelectedListener = NavigationBarView.OnItemSelectedListener  { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                swapFragments(item.itemId, "Home", "#FFFF00")
                return@OnItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                swapFragments(item.itemId, "Dashboard", "#FF00FF")
                return@OnItemSelectedListener  true
            }
            R.id.navigation_notifications -> {
                swapFragments(item.itemId, "Notifications", "#00FFFF")
                return@OnItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            savedStateSparseArray = savedInstanceState.getSparseParcelableArray(SAVED_STATE_CONTAINER_KEY)
                ?: savedStateSparseArray
            currentSelectItemId = savedInstanceState.getInt(SAVED_STATE_CURRENT_TAB_KEY)
        } else {
            swapFragments(R.id.navigation_home, "Home", "#FFFF00")
        }
        setContentView(R.layout.activity_main_conventional)
        findViewById<BottomNavigationView>(R.id.navigation)
            .setOnItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSparseParcelableArray(SAVED_STATE_CONTAINER_KEY, savedStateSparseArray)
        outState.putInt(SAVED_STATE_CURRENT_TAB_KEY, currentSelectItemId)
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
