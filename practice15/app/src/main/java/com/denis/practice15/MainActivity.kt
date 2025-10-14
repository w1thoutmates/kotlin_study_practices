package com.denis.practice15

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.*
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)

            val btnAbsolute = findViewById<Button>(R.id.btn_absolute)
            val btnFrame = findViewById<Button>(R.id.btn_frame)
            val btnLinear = findViewById<Button>(R.id.btn_linear)
            val btnRelative = findViewById<Button>(R.id.btn_relative)
            val btnTable = findViewById<Button>(R.id.btn_table)
            val btnConstraint = findViewById<Button>(R.id.btn_constraint)
            val btnMixed = findViewById<Button>(R.id.btn_mixed)

            btnAbsolute.setOnClickListener {
                startActivity(Intent(this, AbsoluteActivity::class.java))
            }
            btnFrame.setOnClickListener {
                startActivity(Intent(this, FrameActivity::class.java))
            }
            btnLinear.setOnClickListener {
                startActivity(Intent(this, LinearActivity::class.java))
            }
            btnRelative.setOnClickListener {
                startActivity(Intent(this, RelativeActivity::class.java))
            }
            btnTable.setOnClickListener {
                startActivity(Intent(this, TableActivity::class.java))
            }
            btnConstraint.setOnClickListener {
                startActivity(Intent(this,ConstraintActivity::class.java))
            }
            btnMixed.setOnClickListener {
                startActivity(Intent(this, MixedActivity::class.java))
            }

            insets
        }
    }
}