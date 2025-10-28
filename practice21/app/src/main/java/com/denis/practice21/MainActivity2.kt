package com.denis.practice21

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container1, second_fragment())
            .add(R.id.fragment_container2, first_fragment())
            .commit()

        findViewById<Button>(R.id.switchFragment).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container2, second_fragment())
                .replace(R.id.fragment_container1, first_fragment())
                .addToBackStack(null)
                .commit()
        }

        findViewById<Button>(R.id.back).setOnClickListener { finish() }
    }
}