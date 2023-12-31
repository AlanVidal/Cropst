package com.vidal.ephm

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager


object MessageSender {

    @SuppressLint("MutableImplicitPendingIntent")
    public fun sendMessage(message: ByteArray, context: Context, phoneNum: String) {
        val sentPI = PendingIntent.getBroadcast(
            context,
            0x1337,
            Intent(""),
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_MUTABLE
        )
        val deliveryPI = PendingIntent.getBroadcast(
            context,
            0x1337,
            Intent("DELIVER"),
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_MUTABLE
        )

        context.getSystemService(SmsManager::class.java).sendDataMessage(
            phoneNum,
            null,
            9200.toShort(),
            message,
            sentPI,
            deliveryPI
        )

    }

}
