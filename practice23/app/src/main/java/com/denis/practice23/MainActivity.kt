package com.denis.practice23

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.ColumnInfo

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Main()
        }
    }
}

@Composable
fun Main(){
    val owner = LocalViewModelStoreOwner.current

    owner?.let{
        val mammalVM: MammalVM = viewModel(
            it,
            "MammalVM",
            MammalVMFactory(LocalContext.current.applicationContext as Application)
        )

        val dietVM: DietVM = viewModel(
            it,
            "DietVM",
            DietVMFactory(LocalContext.current.applicationContext as Application)
        )

        val dietWithMammalsVM: DietWithMammalsVM = viewModel(
            it,
            "DietWithMammalsVM",
            DietWithMammalsVMFactory(LocalContext.current.applicationContext as Application)
        )

        val mammalsByDietVM: MammalsByDietVM = viewModel(
            it,
            "MammalByDietVM",
            MammalsByDietVMFactory(LocalContext.current.applicationContext as Application)
        )

        var page: Int by remember { mutableStateOf(0) }
        Column(){
            Row(Modifier.padding(top = 55.dp)) {
                Button(onClick = { page = 0 }) { Text("Mammals") }
                Button(onClick = { page = 1 }) { Text("Diets") }
                Button(onClick = { page = 2 }) { Text("D with M") }
                Button(onClick = { page = 3 }) { Text("M. by Diet") }
            }
            if (page == 0)
                MammalsPanel(mammalVM)
            if(page == 1)
                DietsPanel(dietVM)
            if(page == 2)
                DietWithMammalsPanel(dietWithMammalsVM)
            if(page == 3)
                FilterPanel(mammalsByDietVM)
        }
    }
}

@Composable
fun MammalsPanel(vm: MammalVM = viewModel()){
    val mammalList by vm.mammalList.observeAsState(listOf())
    Column{
        AddPanel(listOf(
            TableInfo(vm.mammalId.toString(), "ID", {vm.changeId(it)}),
            TableInfo(vm.mammalName, "Name", {vm.changeName(it)}),
            TableInfo(vm.mammalSpecies, "Species", {vm.changeSpecies(it)}),
            TableInfo(vm.mammalDietId.toString(), "DietID", {vm.changeDietId(it)})
        ), {vm.addMammal()}, {vm.updateMammal()})

        MammalList(mammalList, delete = { vm.deleteMammal(it) })
    }
}

@Composable
fun MammalList(mammals: List<Mammal>, delete: (Int)->Unit){
    LazyColumn(Modifier.fillMaxWidth()) {
        item{ TitleRow("ID", "Name", "Species", "DietID")}
        items(mammals){ mammal ->
            JustRow(
                mammal.mammalId.toString(), mammal.mammalName,
                mammal.species, mammal.mammalDietId.toString(),
                delete = {delete(mammal.mammalId)})
        }
    }
}

@Composable
fun DietsPanel(vm: DietVM = viewModel()){
    val dietList by vm.dietList.observeAsState(listOf())

    Column{
        AddPanel(listOf(
            TableInfo(vm.id.toString(), "ID", {vm.changeId(it)}),
            TableInfo(vm.type, "Type", {vm.changeType(it)}),
            TableInfo(vm.description, "Descr", {vm.changeDescription(it)})
        ), {vm.add()}, {})
    }
    DietList(dietList, delete = { })
}

@Composable
fun DietList(diets: List<Diet>, delete: (Int)->Unit){
    LazyColumn(Modifier.fillMaxWidth()) {
        item{ TitleRow("ID", "Type", "Descr")}
        items(diets){ diet ->
            JustRow(
                diet.dietId.toString(), diet.dietType,
                diet.description, delete = {delete(diet.dietId)})
        }
    }
}

@Composable
fun DietWithMammalsPanel(vm: DietWithMammalsVM = viewModel()){
    val dwmList by vm.dietWithMammalsList.observeAsState(listOf())

    Column{
        DietWithMammalsList(dwmList)
    }
}

@Composable
fun DietWithMammalsList(dietWithMammals: List<DietWithMammals>){
    LazyColumn(Modifier.fillMaxWidth()) {
        item{ TitleRow("Diet", "Name") }
        items(dietWithMammals) { dwm ->
            dwm.mammals.forEach{ mammal ->
                JustRow(dwm.diet.dietType, mammal.mammalName, delete = { })
            }
        }
    }
}

@Composable
fun FilterPanel(vm: MammalsByDietVM = viewModel()){
    var filter by remember {mutableStateOf("")}
    val mammals by vm.getMammalsByDietType(filter).observeAsState(listOf())
    Column(Modifier.padding(8.dp)) {
        OutlinedTextField(
            value = filter,
            onValueChange = { filter = it },
            label = { Text("Diet type") }
        )
        Spacer(Modifier.padding(4.dp))
        LazyColumn {
            item { TitleRow("ID", "Name", "Species") }
            items(mammals) { mammal ->
                JustRow(
                    mammal.mammalId.toString(),
                    mammal.mammalName,
                    mammal.species,
                    delete = {}
                )
            }
        }
    }
}

data class TableInfo(val value: String, val header: String, val onValueChange: (String)->Unit)

@Composable
fun AddPanel(tableInfoes: List<TableInfo>,
             onAdd: () -> Unit, onUpdate: () -> Unit){
    Column{
        tableInfoes.forEach {
                row ->
            OutlinedTextField(
                row.value,
                modifier= Modifier.padding(8.dp), label = { Text(row.header) },
                onValueChange = {row.onValueChange(it)})
        }
        Row(){
            Button({ onUpdate() },
                Modifier.padding(8.dp)) {Text("Update", fontSize = 22.sp)}
            Button({ onAdd() },
                Modifier.padding(8.dp)) {Text("Add", fontSize = 22.sp)}
        }
    }
}

@Composable
fun JustRow(vararg column: String, delete:()->Unit) {
    Row(Modifier.fillMaxWidth().padding(5.dp)) {
        column.forEachIndexed {
                index, value ->
            val weight: Float = if(index == 0) 0.1f else 0.2f
            ColumnText(value, Modifier.weight(weight))
        }
        Text("Delete",
            Modifier.weight(0.2f)
                .clickable { delete() },
            color=Color(0xFF6650a4), fontSize = 22.sp)
    }
}

@Composable
fun ColumnText(text: String, modifier: Modifier, color: Color = Color.Black) {
    Text(text, fontSize = 22.sp, modifier = modifier, color = color)
}

@Composable
fun TitleRow(vararg titles: String) {
    Row(Modifier.background(Color.LightGray).fillMaxWidth().padding(5.dp)) {
        titles.forEachIndexed {
                index, value ->
            val weight: Float = if(index == 0) 0.1f else 0.2f
            ColumnText(value, modifier = Modifier.weight(weight), Color.White)
        }
        Spacer(Modifier.weight(0.2f))
    }
}