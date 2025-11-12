package com.denis.practice22

import android.os.Bundle
import android.text.style.UnderlineSpan
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.denis.practice22.ui.theme.Practice22Theme
import kotlinx.coroutines.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practice22Theme {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                    CornersText()
                    CentersText()
                    Column(
                        modifier = Modifier
                            .align(alignment = Alignment.Center),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        HelloWorld()
                        TextWithDelay()
                        ButtonChangeText()
                        ButtonWithImage()
                        Quiz()
                    }
                }
            }
        }
    }
}

@Composable
fun HelloWorld() {
    Text(
        text="Hello, world!",
        color = Color.Red
    )
}

@Preview(showBackground = true)
@Composable
fun MyPreview() {
    Practice22Theme {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            CornersText()
            CentersText()
            Column(
                modifier = Modifier
                    .align(alignment = Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                HelloWorld()
                TextWithDelay()
                ButtonChangeText()
                ButtonWithImage()
                Quiz()
            }
        }
    }
}

@Composable
fun TextWithDelay(){
    val currentText = remember {mutableStateOf("")}
    val texts = listOf("text 1", "text 2", "text 3", "text 4", "text 5")

    LaunchedEffect(Unit) {
        texts.forEach {
            currentText.value = it
            delay(1000L)
        }
    }

    Text(text=currentText.value)
}

@Composable
fun ButtonChangeText(){
    val text = remember { mutableStateOf("Start Text") }
    Text(text=text.value)
    Button(
        onClick={text.value = "Changed text" },
        colors = ButtonDefaults.buttonColors(
            Color.Blue,
            Color.White)

    ) {
        Text("Change Text")
    }
}

@Composable
fun ButtonWithImage() {
    val text = remember { mutableStateOf("Start Image Text") }
    Text(text = text.value)
    Button(onClick = { text.value = "Changed Text" }) {
        Image(painter = painterResource(R.drawable.ic_launcher_background), contentDescription = null)
//        Text("Change")
    }
}

@Composable
fun Quiz() {
    val selected = remember { mutableStateOf("") }
    val correct = "Option 2"
    val result = remember { mutableStateOf("") }

    Column {
        RadioButton(selected = selected.value == "Option 1", onClick = { selected.value = "Option 1" })
        Text("Option 1")
        RadioButton(selected = selected.value == "Option 2", onClick = { selected.value = "Option 2" })
        Text("Option 2")
        Button(onClick = { result.value = if (selected.value == correct) "Correct" else "Wrong" }) {
            Text("Check")
        }
        Text(result.value)
    }
}

@Composable
fun CornersText(){
    Box(Modifier.fillMaxSize()){
        Text("TopStart", Modifier
            .align(Alignment.TopStart)
            .border(2.dp, Color.Red, RoundedCornerShape(8.dp))
            .padding(8.dp)
        )
        Text("TopEnd", Modifier
            .align(Alignment.TopEnd)
            .offset(x=(-20).dp, y=20.dp)
            .background(Color.LightGray)
        )
        Text("BottomStart", Modifier
            .align(Alignment.BottomStart)
            .offset(y=(-30).dp),
            style = TextStyle(fontSize = 30.sp)
        )
        Box(Modifier.size(150.dp).background(Color.LightGray).align(Alignment.BottomEnd)){
            Text("BottomEnd", Modifier
                .align(Alignment.TopStart)
                .border(4.dp, Color.Blue, androidx.compose.foundation.shape.CircleShape)
                .padding(20.dp, 4.dp, 4.dp, 20.dp)
            )
        }
    }
}

@Composable
fun CentersText() {
    Box(Modifier.fillMaxSize()) {
        Text("TopCenter",
            Modifier.align(Alignment.TopCenter),
            style = TextStyle(textDecoration = TextDecoration.Underline, fontSize = 26.sp)
        )
        Text("CenterEnd",
            Modifier.align(Alignment.CenterEnd),
            style = TextStyle(fontStyle = FontStyle.Italic, fontSize = 26.sp)
        )
        Text("BottomCenter",
            Modifier.align(Alignment.BottomCenter),
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 26.sp)
        )
        Text("CenterStart",
            Modifier.align(Alignment.CenterStart),
            style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold),
            color = Color.Magenta,
            fontFamily = FontFamily.Cursive
        )
    }
}