package com.example.practice27

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practice27.ui.theme.Practice27Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val txt = """
            Вчера, 16.11.2025, мы ходили в парк, а сегодня 17.11.2025 года сидим дома. 
            С 14:30 до 15:45 я играл в игры, потом в 18:20 звонила бабушка.
    
            Мама варит борщ: варила, варит, сварила, варить любит. 
            Папа читает книгу: читал, читает, прочитал, дочитал вчера.
    
            Мой брат (7 лет) всегда хватает телефон и отвечает сам:
    
            - Алло! Мама пошла в магазин, скоро будет!
            - Алё! Это кто? Папа спит, не будите!
            - Да, слушаю! А меня зовут Артём!
            - Алло, мама на кухне варит борщ, не может подойти!
            - Здравствуйте! Это я, Катя! Взрослых нет дома!
            - Аллооо! Говорите, я передам!
            - Да, это мы! А вы кто?
            - Алло, папа в душе, подождите 5 минут!
    
            Вечером смотрели фильм с 20:15 до 22:30. 
            Завтра 18.11.2025 поедем к бабушке в 16:00.
            Телефон: +7 (916) 555-44-33.
            """.trimIndent()
            Practice27Theme {
                RegexFun(txt)
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun MyPreview() {
    val txt = """
        Вчера, 16.11.2025, мы ходили в парк, а сегодня 17 ноября 2025 года сидим дома. 
        С 14:30 до 15:45 я играл в игры, потом в 18:20 звонила бабушка.

        Мама варит борщ: варила, варит, сварила, варить любит. 
        Папа читает книгу: читал, читает, прочитал, дочитал вчера.

        Мой брат (7 лет) всегда хватает телефон и отвечает сам:

        - Алло! Мама пошла в магазин, скоро будет!
        - Алё! Это кто? Папа спит, не будите!
        - Да, слушаю! А меня зовут Артём!
        - Алло, мама на кухне варит борщ, не может подойти!
        - Здравствуйте! Это я, Катя! Взрослых нет дома!
        - Аллооо! Говорите, я передам!
        - Да, это мы! А вы кто?
        - Алло, папа в душе, подождите 5 минут!

        Вечером смотрели фильм с 20:15 до 22:30. 
        Завтра 18.11.2025 поедем к бабушке в 16:00.
        Телефон: +7 (916) 555-44-33.
        """.trimIndent()
    RegexFun(txt)
}

@Composable
fun RegexFun(text: String){
    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
            .padding(16.dp)
    ) {
        val result = buildString {
            val regex1 = text.contains(Regex("мама", RegexOption.IGNORE_CASE))
            appendLine("Есть ли в тексте слово мама: $regex1")

           val varWords = text
                .split(Regex("""\P{L}+"""))
                .map { it.lowercase() }
                .filter { Regex("""\p{L}*вар\p{L}*""", RegexOption.IGNORE_CASE).matches(it) }
                .sorted()

            appendLine("Слова с корнем \"вар\": $varWords")

            val readWords = text
                .split(Regex("""\P{L}+"""))
                .map { it.lowercase() }
                .filter { Regex("""\p{L}*чит\p{L}*""", RegexOption.IGNORE_CASE).matches(it) }
                .sorted()

            appendLine("Слова с корнем \"чит\": $readWords")

            val countOfWords = text
                .split(Regex("""\P{L}+"""))
                .map{it.lowercase()}
                .filter{Regex("""\p{L}{5,}""", RegexOption.IGNORE_CASE).matches(it)}
                .sorted()
                .count()

            appendLine("Количество слов размером больше 5 символов: $countOfWords")

            val dateRegex = Regex(
                """\b(\d{2}\.\d{2}\.\d{4}\b|\b\d{2}\s+\p{L}[а-я]+\s+\d{4})\b""",
                RegexOption.IGNORE_CASE)

            val dates = dateRegex.findAll(text)
                .map { it.value }
                .toList()

            appendLine("Все даты в тексте: $dates")

            val timeRegex = Regex("""\d{2}:\d{2}""",
                RegexOption.IGNORE_CASE)

            val times = timeRegex.findAll(text)
                .map{it.value}
                .toList()

            appendLine("Все время в тексте: $times")

//            val parts = text.split(Regex("""(мама|папа|брат)""", RegexOption.IGNORE_CASE))
//            appendLine("Текст разделенный по частям: $parts[0]") // Разделение текста по частям

            val answerRegex = Regex(
                """(алло+!?|ал(?:е|ё)!?\s*кто это|алло.*кто|да,?\s*слушаю|это я|а меня зовут)""",
                RegexOption.IGNORE_CASE
            )

            val answers = answerRegex.findAll(text).map{it.value}.toList()

            appendLine("Ответы ребенка: $answers")
            
            val regular = text.contains(Regex("папа", RegexOption.IGNORE_CASE))
            appendLine(regular)
        }

        Text(
            text = result,
            fontSize = 14.sp,
            lineHeight = 28.sp
        )
    }
}