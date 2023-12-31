package com.vidal.ephm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log


class MsgReceiver : BroadcastReceiver() {
    val TAG: String = MessageSender::class.simpleName.toString()
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "Received intent  " + intent?.action);
        if (Telephony.Sms.Intents.DATA_SMS_RECEIVED_ACTION != intent?.action) return;
        val extra = intent.extras
        val PDUs = extra?.get("pdus") as Array<*>


        val message = SilentMessage.fromPDU(PDUs[0] as ByteArray) //TODO rename
        MessageNotification.createNotification(context!!, message)//Todo check context

    }


}