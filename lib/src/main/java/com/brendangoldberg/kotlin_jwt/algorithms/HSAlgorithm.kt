package com.brendangoldberg.kotlin_jwt.algorithms

import com.brendangoldberg.kotlin_jwt.Utils

/**
 * HMAC instance of an [Algorithm]
 */
class HSAlgorithm private constructor(
    override val id: String = HS256.first,
    private val algorithm: String = HS256.second,
    private val secret: String
) : Algorithm {

    companion object {

        private val HS256 = "HS256" to "HmacSHA256"
        private val HS384 = "HS384" to "HmacSHA384"
        private val HS512 = "HS512" to "HmacSHA512"

        /**
         * Create HMACSHA256 [Algorithm]
         *
         * @param secret The token secret.
         *
         * @return The HMACSHA256 [Algorithm].
         */
        fun HS256(secret: String): HSAlgorithm {
            return HSAlgorithm(
                HS256.first,
                HS256.second,
                secret
            )
        }

        /**
         * Create HMACSHA384 [Algorithm]
         *
         * @param secret The token secret.
         *
         * @return The HMACSHA384 [Algorithm].
         */
        fun HS384(secret: String): HSAlgorithm {
            return HSAlgorithm(
                HS384.first,
                HS384.second,
                secret
            )
        }

        /**
         * Create HMACSHA512 [Algorithm]
         *
         * @param secret The token secret.
         *
         * @return The HMACSHA512 [Algorithm].
         */
        fun HS512(secret: String): HSAlgorithm {
            return HSAlgorithm(
                HS512.first,
                HS512.second,
                secret
            )
        }

    }

    override fun sign(header: String, payload: String): String {
        return Utils.sign(
            algorithm,
            secret.toByteArray(),
            header,
            payload
        )
    }

    override fun verify(header: String, payload: String, signature: String): Boolean {
        return Utils.verify(algorithm, secret.toByteArray(), header, payload, signature)

    }

}