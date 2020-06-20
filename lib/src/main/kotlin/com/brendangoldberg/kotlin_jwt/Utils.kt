package com.brendangoldberg.kotlin_jwt

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

internal object Utils {
    fun sign(algorithm: String, secret: ByteArray, encodedHeader: String, encodedPayload: String): ByteArray {
        val mac = Mac.getInstance(algorithm)
        mac.init(SecretKeySpec(secret, algorithm))
        val t = "$encodedHeader.$encodedPayload".toByteArray()
        return mac.doFinal(t)
    }
}