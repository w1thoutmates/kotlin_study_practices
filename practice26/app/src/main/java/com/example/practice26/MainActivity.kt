package com.example.practice26

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.practice26.api.*
import com.example.practice26.ui.theme.Practice26Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practice26Theme {
                val vm: CardViewModel = viewModel()
                val card by vm.card.collectAsState()
                val loading by vm.isLoading.collectAsState()
                val error by vm.error.collectAsState()

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = { vm.loadRandomCard() }) {
                        Text("Случайная карта")
                    }

                    Spacer(Modifier.height(32.dp))

                    if (loading) {
                        CircularProgressIndicator()
                    }

                    if (error != null) {
                        Text(error!!, color = MaterialTheme.colorScheme.error)
                    }

                    card?.let {
                        Text("${it.value} ${it.suit}", style = MaterialTheme.typography.headlineMedium)
                        Spacer(Modifier.height(16.dp))
                        Image(
                            painter = rememberAsyncImagePainter(it.image),
                            contentDescription = null,
                            modifier = Modifier.size(250.dp)
                        )
                    }
                }
            }
        }
    }
}

