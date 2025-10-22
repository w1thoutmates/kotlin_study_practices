package com.denis.practice16

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)

            val mainLayout: ScrollView = findViewById(R.id.main)

            val linearLayout = LinearLayout(this).apply{
                orientation = LinearLayout.VERTICAL
            }
            mainLayout.addView(linearLayout)

            val helloText = TextView(this).apply {
                text = "Hello Programmed-View!"
                textSize = 20f
                setTextColor(Color.BLACK)
                gravity = Gravity.CENTER
            }
            linearLayout.addView(helloText)

            val textViews = mutableListOf<TextView>()
            repeat(10) { i ->
                val tv = TextView(this).apply {
                    text = "Какой-то текст №${i+1}"
                    textSize = 16f
                    setTextColor(Color.DKGRAY)
                }
                textViews.add(tv)
                linearLayout.addView(tv)
            }

            val newTextView = TextView(this).apply{
                text = "New text"
                textSize = 20f
            }
            linearLayout.addView(newTextView)

            val buttonsLayout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
            }
            val buttonsList: MutableList<Button> = mutableListOf()
            repeat(10) { i ->
                val btn = Button(this).apply {
                    text = "Кнопка №${i+1}"
                }
                buttonsList.add(btn)
                buttonsLayout.addView(btn)
            }
            linearLayout.addView(buttonsLayout)
            val calcField = EditText(this).apply {
                isFocusable = false
                isClickable = false
                textSize = 24f
                gravity = Gravity.START
                setTextColor(Color.BLACK)
                setBackgroundColor(Color.WHITE)
                hint = "Введите выражение с 2 числами"
            }
            linearLayout.addView(
                calcField,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            )

            val gridCalc = GridLayout(this).apply {
                rowCount = 4
                columnCount = 4
                useDefaultMargins = true
                setPadding(8)
            }
            linearLayout.addView(
                gridCalc,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            )

            val symbols = listOf(
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "C", "0", "=", "+"
            )
            val calcButtons = mutableListOf<Button>()
            for (symbol in symbols) {
                val btn = Button(this).apply {
                    text = symbol
                    textSize = 20f
                    setOnClickListener {
                        when (symbol) {
                            "C" -> calcField.setText("")
                            "=" -> {
                                val expr = calcField.text.toString()
                                val result = calc(expr)
                                calcField.setText(result)
                            }
                            else -> calcField.append(symbol)
                        }
                    }
                }
                gridCalc.addView(btn)
                calcButtons.add(btn)
            }

            val allButtons = buttonsList + calcButtons
            for (btn in allButtons) {
                btn.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFBB86FC"))
                btn.setTextColor(Color.WHITE)
                btn.textSize = 18f
            }

            for (tv in textViews + helloText) {
                tv.setTextSize(24F)
                tv.setTextColor(Color.parseColor("#FF7B4E"))
                tv.setPadding(8)
            }

            insets
        }
    }

    private fun calc(expr: String): String {
        try {
            val parts = expr.split('+', '-', '*', '/')
            if (parts.size != 2) return "В выражении больше 2ух операндов"
            val a = parts[0].toDoubleOrNull() ?: return "Ошибка"
            val b = parts[1].toDoubleOrNull() ?: return "Ошибка"
            val op = expr.find { it in "+-*/" } ?: return "Ошибка"

            val res = when (op) {
                '+' -> a + b
                '-' -> a - b
                '*' -> a * b
                '/' -> if (b != 0.0) a / b else return "Делить на ноль нельзя"
                else -> return "Ошибка"
            }
            return if (res % 1 == 0.0) res.toInt().toString() else res.toString()
        } catch (e: Exception) {
            return "Ошибка"
        }
    }
}