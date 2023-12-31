package com.vidal.ephm

import android.telephony.PhoneNumberUtils
import android.telephony.SmsMessage
import android.util.Log
import java.util.Locale


data class SilentMessage(val message: ByteArray, val from: String) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as SilentMessage
        if (!message.contentEquals(other.message)) return false
        return from == other.from
    }

    override fun hashCode(): Int {
        var result = message.contentHashCode()
        result = 31 * result + from.hashCode()
        return result
    }

    companion object {
        val TAG: String = SilentMessage::class.simpleName.toString()

        fun formatNumber(number: String?): String? {
            return PhoneNumberUtils.formatNumber(number, Locale.getDefault().country)
        }

        public fun fromPDU(pdu: ByteArray): SilentMessage {

            val format = "3gpp2" // not sure if it will always be "3gpp"
            // val sms: SmsMessage = SmsMessage.createFromPdu(pduHexToByteArray(pdu), format)
            val sms: SmsMessage = SmsMessage.createFromPdu(pdu, format)
            val from = formatNumber(sms.originatingAddress)
            Log.d(TAG, "from $from")
            Log.d(TAG, "msg ${sms.userData}")
            return SilentMessage(
                sms.userData,
                from!!
            ) //TODO TODO TODO
        }


    }
}