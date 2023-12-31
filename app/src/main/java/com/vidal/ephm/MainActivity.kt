package com.vidal.ephm

import android.Manifest.permission.POST_NOTIFICATIONS
import android.Manifest.permission.READ_PHONE_STATE
import android.Manifest.permission.RECEIVE_SMS
import android.Manifest.permission.SEND_SMS
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.Locale


class MainActivity : AppCompatActivity() {
    private var sendButton: Button? = null
    private var saveKeyButton: Button? = null
    private var phoneInput: EditText? = null
    private var msgInput: EditText? = null
    private var phoneKey: EditText? = null
    private var passKey: EditText? = null
    private val cryptoEngine: CryptoEngine = CryptoEngine(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()
        initViewAction()
    }

    private fun checkPermission() {

        val sendSmsPermission =
            ContextCompat.checkSelfPermission(this, SEND_SMS)
        val readPhonePermission =
            ContextCompat.checkSelfPermission(this, READ_PHONE_STATE)
        val receiveSmsPermission =
            ContextCompat.checkSelfPermission(this, RECEIVE_SMS)
        val postNotificationPermission =
            ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS)


    }

    private fun initViewAction() {
        sendButton = findViewById(R.id.sendMsg)
        saveKeyButton = findViewById(R.id.addKey)
        msgInput = findViewById(R.id.editTextText3) //TODO rename
        msgInput = findViewById(R.id.editTextText3) //TODO rename
        phoneInput = findViewById(R.id.editTextText2) //TODO rename
        phoneKey = findViewById(R.id.phoneKey) //TODO rename
        passKey = findViewById(R.id.passKey) //TODO rename

        sendButton?.setOnClickListener(View.OnClickListener {
            val msg = msgInput?.text.toString() ?: return@OnClickListener //Todo clean phone number format.
            val number = PhoneNumberUtils.formatNumber(
                phoneInput?.text.toString(),
                Locale.getDefault().country
            ) ?: return@OnClickListener
            Log.d("TEST",number)

            val encrypted: ByteArray = cryptoEngine.encryptAES(msg, number)
            MessageSender.sendMessage(encrypted, this, number);
        })

        saveKeyButton?.setOnClickListener(View.OnClickListener {
            val number = PhoneNumberUtils.formatNumber(
                phoneKey?.text.toString(),
                Locale.getDefault().country
            ) ?: return@OnClickListener
            val key = passKey?.text.toString() ?: return@OnClickListener
            cryptoEngine.saveKey(key, number)
        })
        saveKeyButton?.setOnLongClickListener {
            Log.d("TEST", cryptoEngine.getKeys())
            return@setOnLongClickListener true
        }


    }
}