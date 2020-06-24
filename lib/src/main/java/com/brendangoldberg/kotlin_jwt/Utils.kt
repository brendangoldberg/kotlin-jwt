package com.brendangoldberg.kotlin_jwt

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.toUtf8Bytes
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

internal object Utils {

    val JSON = Json(JsonConfiguration.Stable)
    val DECODER: Base64.Decoder = Base64.getUrlDecoder()
    val ENCODER: Base64.Encoder = Base64.getUrlEncoder().withoutPadding()

    fun sign(algorithm: String, secret: ByteArray, header: String, payload: String): String {
        val mac = Mac.getInstance(algorithm)
        mac.init(SecretKeySpec(secret, algorithm))
        val data = ENCODER.encodeToString(header.toUtf8Bytes()) + "." + ENCODER.encodeToString(payload.toUtf8Bytes())
        return ENCODER.encodeToString(mac.doFinal(data.toUtf8Bytes())).trimEnd('=')
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