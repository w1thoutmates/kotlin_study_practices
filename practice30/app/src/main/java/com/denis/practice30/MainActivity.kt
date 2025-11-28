package com.denis.practice30


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.delay
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ReactiveDemoScreen()
                }
            }
        }
    }
}

class ReactiveViewModel : ViewModel() {

    private var sharedFlowCounter = 0
    private val _sharedFlow = MutableSharedFlow<String>(replay = 1)
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun emitToSharedFlow() {
        viewModelScope.launch {
            sharedFlowCounter++
            _sharedFlow.emit("SharedFlow emit: $sharedFlowCounter")
        }
    }

    private var stateFlowCounter = 0
    private val _stateFlow = MutableStateFlow("Initial State (0)")
    val stateFlow = _stateFlow.asStateFlow()

    fun updateStateFlow() {
        stateFlowCounter++
        _stateFlow.value = "State обновлён: $stateFlowCounter"
    }

    private var liveDataCounter = 0
    private val _liveData = MutableLiveData<String>("Initial LiveData (0)")
    val liveData = _liveData

    fun postToLiveData() {
        liveDataCounter++
        _liveData.value = "LiveData: $liveDataCounter"
    }

    private var channelCounter = 0
    val channel = Channel<String>(Channel.UNLIMITED)

    fun sendToChannel() {
        viewModelScope.launch {
            channelCounter++
            channel.send("Channel сообщение: $channelCounter")
        }
    }
}

@Composable
fun ReactiveDemoScreen(viewModel: ReactiveViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()

    val stateFlowValue by viewModel.stateFlow.collectAsState()

    val sharedFlowValue by viewModel.sharedFlow.collectAsState(initial = "Ещё нет emit")

    val liveDataValue by viewModel.liveData.observeAsState("Initial LiveData")

    val channelMessages = remember { mutableStateListOf<String>() }
    LaunchedEffect(Unit) {
        viewModel.channel.receiveAsFlow().collect {
            channelMessages.add(it)
        }
    }

    var flowCollector1 by remember { mutableStateOf("") }
    var flowCollector2 by remember { mutableStateOf("") }

    var flowBaseCounter by remember { mutableStateOf(0) }
    val coldFlow = {
        flow {
            val base = ++flowBaseCounter
            emit("Start: Base $base")
            delay(800)
            emit("Value 1 from $base")
            delay(800)
            emit("Value 2 from $base")
            delay(800)
            emit("End from $base")
        }
    }

    var countRemember by remember { mutableStateOf(0) }
    var countSaveable by rememberSaveable { mutableStateOf(0) }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            Text("Flow")
            Text("Холодный поток")

            Row {
                Button(onClick = {
                    coroutineScope.launch {
                        flowCollector1 = ""
                        coldFlow().collect {
                            flowCollector1 += "$it | "
                        }
                    }
                }) {
                    Text("Запустить коллектор 1")
                }
                Spacer(Modifier.width(8.dp))
                Button(onClick = {
                    coroutineScope.launch {
                        flowCollector2 = ""
                        coldFlow().collect {
                            flowCollector2 += "$it | "
                        }
                    }
                }) {
                    Text("Запустить коллектор 2")
                }
            }
            Text("Коллектор 1: $flowCollector1")
            Text("Коллектор 2: $flowCollector2")

            Spacer(Modifier.height(20.dp))

            Text("SharedFlow")
            Text("Горячий поток: $sharedFlowValue")
            Button(onClick = {
                viewModel.emitToSharedFlow()
            }) {
                Text("Emit в SharedFlow")
            }

            Spacer(Modifier.height(20.dp))

            Text("StateFlow")
            Text("Горячий поток: $stateFlowValue")
            Button(onClick = {
                viewModel.updateStateFlow()
            }) {
                Text("Обновить StateFlow")
            }

            Spacer(Modifier.height(20.dp))

            Text("LiveData / MutableLiveData")
            Text("Осведомлены: $liveDataValue")
            Button(onClick = {
                viewModel.postToLiveData()
            }) {
                Text("Post в LiveData")
            }

            Spacer(Modifier.height(20.dp))

            Text("Channel")
            Button(onClick = {
                viewModel.sendToChannel()
            }) {
                Text("Отправить в Channel")
            }
            Text("Получено: ${channelMessages.joinToString(" -> ")}")

            Spacer(Modifier.height(20.dp))

            Text("remember() / rememberSaveable()")

            Button(onClick = { countRemember++ }) {
                Text("remember: $countRemember ")
            }
            Spacer(Modifier.height(8.dp))
            Button(onClick = { countSaveable++ }) {
                Text("rememberSaveable: $countSaveable ")
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}