package com.elyeproj.bottombarfragmentsswitching

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.core.view.forEach
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.fragment.fragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.elyeproj.bottombarfragmentsswitching.common.ContainerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.ref.WeakReference

class MainNavigationCodeRouteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_navigation_code)

        val navigation: BottomNavigationView = findViewById(R.id.navigation)
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navController.graph = navController.createGraph(startDestination = navRoutes.home) {
            registerFragment(navRoutes.home, getString(R.string.title_home), "#FFFF00")
            registerFragment(navRoutes.dashboard, getString(R.string.title_dashboard), "#FF00FF")
            registerFragment(navRoutes.notifications, getString(R.string.title_notifications), "#00FFFF")
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val actionBar = checkNotNull(supportActionBar) {
                "Activity ${this@MainNavigationCodeRouteActivity} does not have an ActionBar set via setSupportActionBar()"
            }
            actionBar.title = destination.label
        }

        navigation.setupWithNavControllerCustom(navController)
    }

    private fun NavGraphBuilder.registerFragment(route: String, title: String, color: String) {
        fragment<ContainerFragment>(route) {
            label = title
            argument(navArgument.fragment_key) {
                type = NavType.StringType
                defaultValue = title
            }
            argument(navArgument.fragment_color) {
                type = NavType.StringType
                defaultValue = color
            }
        }
    }

    private fun BottomNavigationView.setupWithNavControllerCustom(
        navController: NavController
    ) {
        val builder = NavOptions.Builder().setLaunchSingleTop(true).setRestoreState(true)
        builder.setPopUpTo(
            navController.graph.findStartDestination().id,
            inclusive = false,
            saveState = true
        )
        val options = builder.build()

        setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(navRoutes.home, options)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_dashboard -> {
                    navController.navigate(navRoutes.dashboard, options)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_notifications -> {
                    navController.navigate(navRoutes.notifications, options)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    object navRoutes {
        const val home = "home"
        const val dashboard = "dashboard"
        const val notifications = "notifications"
    }

    object navArgument {
        const val fragment_key = "FragmentKey"
        const val fragment_color = "FragmentColor"
    }
}
