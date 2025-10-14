package com.denis.practice15

import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MixedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mixed)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)

            val btnBack = findViewById<Button>(R.id.btn_back)
            btnBack.setOnClickListener { finish() }

            val btnLike = findViewById<Button>(R.id.btn_like)
            val btnDislike = findViewById<Button>(R.id.btn_dislike)

            btnLike.setOnClickListener {
                Toast.makeText(
                    this,
                    "Вам нравится этот товар! :)",
                    Toast.LENGTH_SHORT).show()
            }

            btnDislike.setOnClickListener {
                Toast.makeText(
                    this,
                    "Вам не нравится этот товар. :(",
                    Toast.LENGTH_SHORT).show()
            }

            insets
        }
    }
}