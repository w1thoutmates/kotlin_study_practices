package com.denis.practice29

import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import com.denis.practice29.ui.theme.Practice29Theme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val notificationChannelGroup = NotificationChannelGroup("russian", "Россия")

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannelGroup(notificationChannelGroup)

        val channelId = "my_channel_id"
        val channelName = "Первый"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(
            channelId, channelName, importance
        ).apply {
            description = "Российский канал"
            group = "russian"
        }
        notificationManager.createNotificationChannel(channel)

        setContent {
            Practice29Theme {
                Fun()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyPreview(){
    Fun();
}

@Composable
fun Fun(){
    val context = LocalContext.current
    Column(
        Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ){
        Button(onClick = {makeNotification(context)}){
            Text("Notify me")
        }
    }
}

fun makeNotification(context: Context){
    // Создание уведомления
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val chanelId = "my_channel_id"

    val pendingIntent = PendingIntent.getActivity(
        context, 0,
        Intent(context, MainActivity::class.java),
        PendingIntent.FLAG_MUTABLE
    )

    // Создание действия с Remote Input
    val replyAction = NotificationCompat.Action.Builder(
        R.mipmap.ic_launcher_round,
        "Reply",
        pendingIntent
    ).addRemoteInput(
        RemoteInput.Builder("key_text_reply")
            .setLabel("Reply's text")
            .build()
    ).build()

    val builder = NotificationCompat.Builder(context, chanelId)
        .setSmallIcon(R.mipmap.ic_launcher_round)
        .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
        .addAction(
            R.mipmap.ic_launcher_round,
            "1",
            pendingIntent
        )
        .addAction(
            R.mipmap.ic_launcher_round,
            "2",
            pendingIntent
        )
        .addAction(replyAction)
        .setContentTitle("Заголовок!")
        .setContentText("Текст уведомления.")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .build()

    notificationManager.notify(System.currentTimeMillis().toInt(), builder)
}
