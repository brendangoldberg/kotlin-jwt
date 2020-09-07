package com.brendangoldberg.kotlin_jwt

import kotlinx.serialization.json.Json
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

internal object Utils {

    val JSON = Json {  }
    val DECODER: Base64.Decoder = Base64.getUrlDecoder()
    val ENCODER: Base64.Encoder = Base64.getUrlEncoder().withoutPadding()

    fun sign(algorithm: String, secret: ByteArray, header: String, payload: String): String {
        val mac = Mac.getInstance(algorithm)
        mac.init(SecretKeySpec(secret, algorithm))
        val data = ENCODER.encodeToString(header.toByteArray()) + "." + ENCODER.encodeToString(payload.toByteArray())
        return ENCODER.encodeToString(mac.doFinal(data.toByteArray())).trimEnd('=')
    }

    fun verify(
        algorithm: String,
        secret: ByteArray,
        header: String,
        payload: String,
        signature: String
    ): Boolean {
        val other = sign(algorithm, secret, header, payload)
        return signature == other
    }
}