package com.brendangoldberg.kotlin_jwt

import com.brendangoldberg.kotlin_jwt.algorithms.Algorithm

class KtJwtVerifier(
    private val algorithm: Algorithm
) {

    companion object {
        private val decoder = Utils.DECODER
    }

    fun verify(jwt: String): Boolean {
        val parts = jwt.split(".")
        val tHeader = String(decoder.decode(parts[0]))
        val tPayload = String(decoder.decode(parts[1]))
        val tSignature = parts[2]

        return algorithm.verify(tHeader, tPayload, tSignature)
    }

}