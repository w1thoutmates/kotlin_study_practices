package com.denis.practice21

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container1, first_fragment())
            .add(R.id.fragment_container2, second_fragment())
            .commit()


        findViewById<Button>(R.id.toMainActivity2).setOnClickListener { startActivity(Intent(this,
            MainActivity2::class.java)) }

        findViewById<Button>(R.id.toActivity3).setOnClickListener { startActivity(Intent(this,
            ActivityWithList::class.java)) }

        findViewById<Button>(R.id.openFragment).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container1, BlankFragment())
                .commit()
        }
    }
}