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
     * Attempts to create a signature given JWT header, and payload.
     *
     * @param header    The JWT header.
     * @param payload   The JWT payload.
     *
     * @see <a href = "https://jwt.io/introduction/">JWT Introduction</a>
     *
     * @return The signature.
     */
    fun sign(header: String, payload: String): String

    fun verify(header: String, payload: String, signature: String): Boolean
}