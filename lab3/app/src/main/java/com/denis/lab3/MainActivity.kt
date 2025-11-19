package com.denis.lab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.denis.lab3.api.DogViewModel
import com.denis.lab3.ui.theme.Lab3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab3Theme {
                val vm: DogViewModel = viewModel()
                val loading by vm.isLoading.collectAsState()
                val error by vm.error.collectAsState()
                val helloMessage by vm.helloMessage.collectAsState()
                val dogMessage by vm.dogMessage.collectAsState()
                val calcResult by vm.calcResult.collectAsState()

                var a by remember { mutableStateOf("") }
                var b by remember { mutableStateOf("") }
                var dogName by remember { mutableStateOf("") }
                var dogBreed by remember { mutableStateOf("") }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Button(onClick = {vm.loadHello()}, enabled = !loading){
                        Text("Get Hello")
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = a,
                            onValueChange = { a = it },
                            label = { Text("A number") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = b,
                            onValueChange = { b = it },
                            label = { Text("B number") },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Button(
                        onClick = {
                            val numA = a.toIntOrNull() ?: 0
                            val numB = b.toIntOrNull() ?: 0
                            vm.calculate(numA, numB)
                        },
                        enabled = !loading
                    ){
                        Text("Calculate A+B")
                    }

                    OutlinedTextField(
                        value = dogName,
                        onValueChange = { dogName = it },
                        label = { Text("Dog's name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = dogBreed,
                        onValueChange = { dogBreed = it },
                        label = { Text("Dog's breed") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            vm.addNewDog(dogName, dogBreed)
                            dogName = ""
                            dogBreed = ""
                        },
                        enabled = !loading && dogName.isNotEmpty() && dogBreed.isNotEmpty()
                    ){
                        Text("Add new dog")
                    }

                    if(loading){
                        CircularProgressIndicator()
                    }

                    if (error != null) {
                        Text(error!!, color = MaterialTheme.colorScheme.error)
                    }
                    helloMessage?.let { Text("Hello: $it") }
                    calcResult?.let { Text("Calculation: $it") }
                    dogMessage?.let { Text("Dog: $it") }
                }
            }
        }
    }
}


