package com.denis.practice13

import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import android.widget.*
import androidx.core.view.marginTop

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)

            val myLinearLayout = findViewById<LinearLayout>(R.id.linearContainer)
            val changeTextButton = findViewById<Button>(R.id.changeTxtBtn)
            var counter = 0
            val changeTextImageButton = findViewById<ImageButton>(R.id.changeTxtImageBtn)
            val testLinearLayout = findViewById<LinearLayout>(R.id.testLinearLayout)

            val frames = listOf(
                R.drawable.one,
                R.drawable.two
            )

            val gif = findViewById<ImageView>(R.id.gifIv)

            lifecycleScope.launch {
                repeat(5){i ->
                    val bgColor = if (i % 2 == 0) 0xFFCA03FC.toInt() else 0xFF1C1C1C.toInt()
                    val txtColor = if (i % 2 == 0) 0xFF050505.toInt() else 0xFFFDFDFD.toInt()

                    myLinearLayout.addView(TextView(this@MainActivity).apply{
                        setText("Hello World! (${i + 1})")
                        setTextSize(24.0f)
                        setBackgroundColor(bgColor)
                        setTextColor(txtColor)
                        gravity = android.view.Gravity.CENTER
                        id = 10 + i
                    })
                    delay(1000L)
                }
            }

            lifecycleScope.launch {
                while (true){
                    for(frame in frames){
                        runOnUiThread {
                            gif.setImageResource(frame)
                        }
                        delay(200L)
                    }
                }
            }

            changeTextButton.setOnClickListener {
                if(counter < 5){
                    val tvToChange = findViewById<TextView>(10 + counter)
                    tvToChange.apply{
                        setText("Changed text (${counter + 1})")
                    }
                    counter++
                } else {
                    Toast.makeText(
                        this,
                        "All text inside the buttons already changed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            changeTextImageButton.setOnClickListener {
                if(counter < 5){
                    val tvToChange = findViewById<TextView>(10 + counter)
                    tvToChange.apply{
                        setText("Changed text (${counter + 1})")
                    }
                    counter++
                } else {
                    Toast.makeText(
                        this,
                        "All text inside the buttons already changed.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            val questionText = TextView(this@MainActivity).apply{
                text = "What is not an animal?"
                textSize = 20f
                setTextColor(0xFF000000.toInt())
                setBackgroundColor(0xFFFDFDFD.toInt())
            }
            testLinearLayout.addView(questionText)

            val radioGroup = RadioGroup(this@MainActivity).apply{
                setBackgroundColor(0xFFFDFDFD.toInt())
            }
            val options = listOf("Fox", "Wolf", "Armchair", "Bear")
            options.forEachIndexed { i, opt ->
                val rb = RadioButton(this@MainActivity).apply{
                    text = opt
                    id = 100 + i
                }
                radioGroup.addView(rb)
            }
            testLinearLayout.addView(radioGroup)

            val submit = Button(this@MainActivity).apply{
                text = "Submit the answer"
            }
            testLinearLayout.addView(submit)

            submit.setOnClickListener {
                val selectedId = radioGroup.checkedRadioButtonId
                if (selectedId == -1) return@setOnClickListener

                val selectedText = findViewById<RadioButton>(selectedId).text
                val correctAnswer = "Armchair"

                if(selectedText == correctAnswer) Toast.makeText(
                    this, "Correct!", Toast.LENGTH_SHORT
                ).show()
                else Toast.makeText(
                    this, "Incorrect, the correct answer is $correctAnswer", Toast.LENGTH_SHORT
                ).show()
            }

            insets
        }
    }
}