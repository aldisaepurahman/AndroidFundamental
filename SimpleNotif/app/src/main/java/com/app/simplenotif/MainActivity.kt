package com.app.simplenotif

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {
    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "channel_01"
        private const val CHANNEL_NAME = "dicoding channel"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun sendNotification(view: View) {
        /*membuat pendingintent untuk mengirimkan notifikasi*/
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://dicoding.com"))
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        /*membuat detail notifikasi*/
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        val mBuilder = NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setContentIntent(pendingIntent)
            setSmallIcon(R.drawable.ic_notifications_white_48px)
            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_notifications_white_48px))
            setContentTitle(resources.getString(R.string.content_title))
            setContentText(resources.getString(R.string.content_text))
            setSubText(resources.getString(R.string.subtext))
            setAutoCancel(true)
        }

        /*khusus user Android Oreo ke atas, perlu penambahan pembuatan channel*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /*buat atau update channel*/
            val channel = NotificationChannel(CHANNEL_ID,
                CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = CHANNEL_NAME
            mBuilder.setChannelId(CHANNEL_ID)
            mNotificationManager.createNotificationChannel(channel)
        }

        /*mengirimkan notifikasi*/
        val notification = mBuilder.build()
        mNotificationManager.notify(NOTIFICATION_ID, notification)
    }
}