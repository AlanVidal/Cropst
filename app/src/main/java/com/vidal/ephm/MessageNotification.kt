package com.vidal.ephm

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat


//Todo refactoring needed.
object MessageNotification {
    private const val CHAN_DESC: String = "Tempo" //TODO push in R
    private const val CHAN_ID: String = "EphMessage"

    fun createNotification(context: Context, message: SilentMessage) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val msg = CryptoEngine(context).decryptAESToString(message.message, message.from)

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(
            context, CHAN_ID
        )
            .setSmallIcon(R.drawable.ic_dialog_alert) // Set Ticker Message
            .setContentTitle(msg) //Todo check name is contacts.
            .setSubText(message.from)
            // .setContentText(message?.decryptedMsg()) //TODO add in r.strings.
            .setAutoCancel(true)
        val mChannel = NotificationChannel(
            CHAN_ID,
            CHAN_DESC, NotificationManager.IMPORTANCE_HIGH
        )
        // Configure the notification channel.
        mChannel.description = "TEMPO"
        mChannel.enableLights(true)
        mChannel.enableVibration(true)
        notificationManager.createNotificationChannel(mChannel)
        // Build Notification with Notification Manager
        notificationManager.notify(message.hashCode(), builder.build())
    }
}