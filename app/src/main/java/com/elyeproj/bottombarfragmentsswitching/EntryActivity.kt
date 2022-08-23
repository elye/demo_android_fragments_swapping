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
    }
}