package com.denis.practice18

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)

            findViewById<TextView>(R.id.textView).text = getString(R.string.hello)

            val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
            val rotate = AnimationUtils.loadAnimation(this, R.anim.rotate)
            val heartBeat = AnimationUtils.loadAnimation(this, R.anim.heart_beat)

            findViewById<TextView>(R.id.textView).startAnimation(fadeIn)
            findViewById<Button>(R.id.button).startAnimation(rotate)
            findViewById<ImageView>(R.id.imageView).startAnimation(heartBeat)

            findViewById<Button>(R.id.toSecondActivity).setOnClickListener {
                startActivity(Intent(this, Activity2::class.java))
            }

            findViewById<Button>(R.id.toThirdActivity).setOnClickListener {
                startActivity(Intent(this, Activity3::class.java))
            }

            insets
        }

        val navView = findViewById<BottomNavigationView>(R.id.nav)
        navView?.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_activity2 -> {
                    startActivity(Intent(this, Activity2::class.java))
                    true
                }
                R.id.nav_activity3 -> {
                    startActivity(Intent(this, Activity3::class.java))
                    true
                }
                else -> false
            }
        }

    }
}