package com.vidal.ephm

import android.content.Context
import android.util.Log
import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


class CryptoEngine(val context: Context) {

    val TAG: String = CryptoEngine::class.simpleName.toString()
    val iv: IvParameterSpec = IvParameterSpec(ByteArray(16)) //TODO rmeove.
    public fun stringToAESKey(string: String) {}
    public fun diffieHellman(string: String) {}

    public fun encryptAES(msg: String, phoneNumber: String): ByteArray {
        val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val key = getKey(phoneNumber) ?: throw Exception("No key") //TODO error must be created.
        cipher.init(Cipher.ENCRYPT_MODE, passToAESKey(key), iv);
        return cipher.doFinal(msg.encodeToByteArray())

    }

    public fun decryptAES(
        encryptedMsg: ByteArray,
        phoneNumber: String
    ): ByteArray { //TODO refactoring with encrypt
        val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val key = getKey(phoneNumber) ?: throw Exception("No key") //TODO error must be created.
        cipher.init(Cipher.DECRYPT_MODE, passToAESKey(key), iv);
        return cipher.doFinal(encryptedMsg)
    }

    fun decryptAESToString(
        encryptedMsg: ByteArray,
        phoneNumber: String
    ): String {
        val decrypted = decryptAES(encryptedMsg, phoneNumber)
        return String(decrypted, StandardCharsets.UTF_8)

    }

    fun addPassKey(phoneNumber: String, passKey: String) {

    }


    //To remove...
    fun passToAESKey(pass: String): SecretKey {
        var fullKey = pass;
        while (fullKey.length < 32) {
            fullKey += pass
        }
        fullKey = fullKey.substring(0, 32)
        return SecretKeySpec(fullKey.encodeToByteArray(), 0, fullKey.length, "AES")
    }


    //TODO remove and use StrongBox
    //Very ugly f()
    //Data must be encrypted
    fun saveKey(newKey: String, phoneNumber: String) {
        val data = "${phoneNumber}:$newKey"
        val sharedPref = context.getSharedPreferences("Keys", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(
                "AES",
                getKeys() + ";" + data
            ) //TODO must be remove, only in PoC, must be removed with Room and StrongBox !
            apply()
        }
    }

    //TODO remove and use StrongBox
    fun getKeys(): String {
        val sharedPref = context.getSharedPreferences("Keys", Context.MODE_PRIVATE)
        return sharedPref.getString("AES", "") ?: ""
    }

    //TODO Must be completely removed
    fun getKey(phoneNumber: String): String? {
        val keyPairList = getKeys().split(";")
        for (keyPair in keyPairList) {
            Log.d(TAG, keyPair)
            val pair = keyPair.split(":")
            if (pair.size == 2)
                if (pair[0].contains(phoneNumber)) return pair[1]
        }
        return null;
    }
}
