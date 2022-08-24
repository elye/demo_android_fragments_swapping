package com.elyeproj.bottombarfragmentsswitching

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.createGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.fragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.elyeproj.bottombarfragmentsswitching.common.ContainerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainNavigationCodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_navigation_code)

        val navigation: BottomNavigationView = findViewById(R.id.navigation)
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navController.graph = navController.createGraph(startDestination = R.id.navigation_home) {
            registerFragment(R.id.navigation_home, getString(R.string.title_home), "#FFFF00")
            registerFragment(R.id.navigation_dashboard, getString(R.string.title_dashboard), "#FF00FF")
            registerFragment(R.id.navigation_notifications, getString(R.string.title_notifications), "#00FFFF")
        }

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        navigation.setupWithNavController(navController)
    }

    private fun NavGraphBuilder.registerFragment(@IdRes id: Int, title: String, color: String) {
        fragment<ContainerFragment>(id) {
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

    object navArgument {
        const val fragment_key = "FragmentKey"
        const val fragment_color = "FragmentColor"
    }
}
