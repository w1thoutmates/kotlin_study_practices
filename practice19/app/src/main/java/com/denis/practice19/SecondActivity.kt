package com.denis.practice19

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textView = findViewById<TextView>(R.id.tv1)
        val returnedText = intent.getStringExtra("dataFromMain")
        textView.text = returnedText
        val textToBeReturned = "Со второго активити: ${returnedText}"
        val userObject = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            intent.getParcelableExtra<User>("denisUser", User::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<User>("denisUser")
        }

        val classObjectTV = findViewById<TextView>(R.id.tv2)
        classObjectTV.text = "Объект класса:\nname = ${userObject?.name}, age = ${userObject?.age}, gender = ${userObject?.gender}"


        findViewById<Button>(R.id.back).setOnClickListener {
            val returnIntent = Intent()
            returnIntent.putExtra("dataFromSecond", textToBeReturned)
            setResult(RESULT_OK, returnIntent)
            finish()
        }

    }
}