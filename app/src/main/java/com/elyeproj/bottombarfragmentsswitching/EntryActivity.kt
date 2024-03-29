package com.elyeproj.bottombarfragmentsswitching

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class EntryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        findViewById<Button>(R.id.button_conventional_swap).setOnClickListener {
            startActivity(Intent(this, MainConventionalActivity::class.java))
        }

        findViewById<Button>(R.id.button_navigation_swap).setOnClickListener {
            startActivity(Intent(this, MainNavigationActivity::class.java))
        }

        findViewById<Button>(R.id.button_navigation_code_swap).setOnClickListener {
            startActivity(Intent(this, MainNavigationCodeActivity::class.java))
        }

        findViewById<Button>(R.id.button_navigation_code_route_swap).setOnClickListener {
            startActivity(Intent(this, MainNavigationCodeRouteActivity::class.java))
        }
    }
}