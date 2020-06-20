package com.brendangoldberg.kotlin_jwt.algorithms

/**
 * Defines an algorithm used for signing a JWT.
 */
interface Algorithm {

    /**
     * ID used for JWT header.
     *
     * @see <a href = "https://jwt.io/introduction/">JWT Introduction</a>
     */
    val id: String

    /**
     * Attempts to create a signature given an encoded header, and payload.
     *
     * @param encodedHeader     The Base64 URL encoded header.
     * @param encodedPayload    The Base64 URL encoded payload.
     *
     * @see <a href = "https://jwt.io/introduction/">JWT Introduction</a>
     *
     * @return The signature.
     */
    fun sign(encodedHeader: String, encodedPayload: String): ByteArray
}