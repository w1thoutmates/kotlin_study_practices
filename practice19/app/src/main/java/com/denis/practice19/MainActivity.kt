package com.denis.practice19

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
          if(result.resultCode == RESULT_OK){
              val data: Intent? = result.data
              val returnedData = data?.getStringExtra("dataFromSecond") ?: "Нет данных"
              findViewById<TextView>(R.id.returnedTextFromSecond).text = returnedData
          }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 0)
        }

        val btnToSecondAct = findViewById<Button>(R.id.toSecondAct)
        val editText = findViewById<EditText>(R.id.et1)
        val confirmEditText = findViewById<Button>(R.id.confirmEt1)
        val savedText = findViewById<TextView>(R.id.savedText)
        val returnedText = findViewById<TextView>(R.id.returnedTextFromSecond)

        btnToSecondAct.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("dataFromMain", savedText.text.toString())
            val denisUser = User("Denis", 19, "M")
            val bundle = Bundle()
            bundle.putParcelable("denisUser", denisUser)
            intent.putExtras(bundle)

            resultLauncher.launch(intent)
        }

        confirmEditText.setOnClickListener {
            savedText.text = editText.text
            editText.text.clear()
        }

        returnedText.text = if(returnedText.toString().isEmpty())
            intent.getStringExtra("dataFromSecond")
        else
            "Тут будет текст который вы получите из второго активити"

        findViewById<Button>(R.id.urlIntent).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)

            intent.data = Uri.parse("https://google.com")
            startActivity(intent)
        }

        findViewById<Button>(R.id.cameraIntent).setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(intent)
        }
    }
}