package com.denis.practice20

import android.content.Intent
import android.content.Intent.ACTION_OPEN_DOCUMENT
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    val MY_REQUEST_CODE1 = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editText = findViewById<EditText>(R.id.enterUrlField)
        lateinit var url: String

        findViewById<Button>(R.id.submitUrlBtn).setOnClickListener {
            url = editText.text.toString()
//            editText.text.clear()

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)

            startActivity(intent)
        }

        findViewById<Button>(R.id.openYoutubeBtn).setOnClickListener {
            url = "https://youtu.be/u_pnia4Xhlw?si=wxI9dPqAlff4cdv9" // видео "режу воду 10 часов"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)

            startActivity(intent)
        }

        val myButton = findViewById<Button>(R.id.pickPhotoForBackgroundBtn)

        myButton.setOnClickListener {
            val intent = Intent(ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "*/*"
            intent.putExtra(Intent.EXTRA_MIME_TYPES, "image/*")
            val intentChooser = Intent.createChooser(intent, "Выберите приложение")

            startActivityForResult(intentChooser, MY_REQUEST_CODE1)
        }

        findViewById<Button>(R.id.callBtn).setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:1337228666")
            startActivity(intent)
        }

        findViewById<Button>(R.id.someBtn).setOnClickListener {
            url = "https://startandroid.ru/ru/uroki.html"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            val intentChooser = Intent.createChooser(intent, "Выберите приложение: ")
            startActivity(intentChooser)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == MY_REQUEST_CODE1) {

            val uriFilePathToGallery = data?.data
            showURIOnBackground(uriFilePathToGallery)

        }
    }

    fun showURIOnBackground(uri: Uri?) {
        uri?.let {
            val inputStream = contentResolver.openInputStream(it)
            inputStream?.let{ stream ->
                val bitmap = BitmapFactory.decodeStream(stream)
                stream.close()
                val drawable = BitmapDrawable(resources, bitmap)
                val main = findViewById<View>(R.id.main)
                main.background = drawable
            }
        }
    }

}