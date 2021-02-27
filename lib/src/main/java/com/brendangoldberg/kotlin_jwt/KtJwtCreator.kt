package com.brendangoldberg.kotlin_jwt

import com.brendangoldberg.kotlin_jwt.algorithms.Algorithm
import com.brendangoldberg.kotlin_jwt.serializers.DateSerializer
import com.brendangoldberg.kotlin_jwt.serializers.LocalDateTimeSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.LinkedHashMap

/**
 * Main class for creating JWT tokens.
 */
class KtJwtCreator private constructor() {

    companion object {
        private val S_STRING = String.serializer()
        private val S_STRING_LIST = ListSerializer(String.serializer())
        private val S_DOUBLE = Double.serializer()
        private val S_LONG = Long.serializer()
        private val S_INT = Int.serializer()
        private val S_BOOLEAN = Boolean.serializer()

        private val encoder = Utils.ENCODER

        private val json = Utils.JSON

        /**
         * Initializes a [Builder] instance.
         *
         * @return The generated [Builder] instance.
         */
        fun init(): Builder = Builder()

        private fun String.toJson(): JsonElement {
            return json.encodeToJsonElement(S_STRING, this)
        }

        private fun Double.toJson(): JsonElement {
            return json.encodeToJsonElement(S_DOUBLE, this)
        }

        private fun Long.toJson(): JsonElement {
            return json.encodeToJsonElement(S_LONG, this)
        }

        private fun Int.toJson(): JsonElement {
            return json.encodeToJsonElement(S_INT, this)
        }

        private fun Boolean.toJson(): JsonElement {
            return json.encodeToJsonElement(S_BOOLEAN, this)
        }

        private fun Date.toJson(): JsonElement {
            return json.encodeToJsonElement(DateSerializer, this)
        }

        private fun LocalDateTime.toJson(): JsonElement {
            return json.encodeToJsonElement(LocalDateTimeSerializer, this)
        }

        private fun List<String>.toJson(): JsonElement {
            return json.encodeToJsonElement(S_STRING_LIST, this)
        }
    }

    private val header = LinkedHashMap<String, JsonElement>()
    private val payload = LinkedHashMap<String, JsonElement>()

    private fun sign(algorithm: Algorithm): String {
        header.putIfAbsent(Constants.ALGORITHM, algorithm.id.toJson())

        val headerJson = JsonObject(header).toString()
        val payloadJson = JsonObject(payload).toString()

        val headerBytes = headerJson.toByteArray()
        val payloadBytes = payloadJson.toByteArray()

        val tHeader = encoder.encodeToString(headerBytes)
        val tPayload = encoder.encodeToString(payloadBytes)

        val signature = algorithm.sign(headerJson, payloadJson)

        return "$tHeader.$tPayload.$signature"
    }

    class Builder {

        private val jwt = KtJwtCreator()

        /**
         * Adds item to JWT payload.
         *
         * @param key   The payload key.
         * @param value The payload value.
         *
         * @return  The [Builder] instance.
         */
        fun addClaim(key: String, value: String) = this.apply {
            jwt.payload[key] = value.toJson()
        }

        /**
         * Adds item to JWT payload.
         *
         * @param key   The payload key.
         * @param value The payload value.
         *
         * @return  The [Builder] instance.
         */
        fun addClaim(key: String, value: Boolean) = this.apply {
            jwt.payload[key] = value.toJson()
        }

        /**
         * Adds item to JWT payload.
         *
         * @param key   The payload key.
         * @param value The payload value.
         *
         * @return  The [Builder] instance.
         */
        fun addClaim(key: String, value: Int) = this.apply {
            jwt.payload[key] = value.toJson()
        }

        /**
         * Adds item to JWT payload.
         *
         * @param key   The payload key.
         * @param value The payload value.
         *
         * @return  The [Builder] instance.
         */
        fun addClaim(key: String, value: Long) = this.apply {
            jwt.payload[key] = value.toJson()
        }

        /**
         * Add item to payload of JWT.
         *
         * @param key   The payload key.
         * @param value The payload value.
         *
         * @return  The [Builder] instance.
         */
        fun addClaim(key: String, value: Double) = this.apply {
            jwt.payload[key] = value.toJson()
        }

        /**
         * Add item to payload of JWT.
         *
         * @param key           The payload key.
         * @param value         The payload value.
         * @param serializer    The [kotlinx.serialization.KSerializer] for value.
         *
         * @return  The [Builder] instance.
         */
        fun <T> addClaim(key: String, value: T, serializer: KSerializer<T>) = this.apply {
            jwt.payload[key] = json.encodeToJsonElement(serializer, value)
        }

        /**
         * Set JWT issuer "iss".
         *
         * @param value The JWT issuer to set.
         *
         * @see <a href="https://tools.ietf.org/html/rfc7519#section-4.1.1">RFC-7519</a>
         *
         * @return  The [Builder] instance.
         */
        fun setIssuer(value: String) = this.apply {
            jwt.payload.putIfAbsent(Constants.ISSUER, value.toJson())
        }

        /**
         * Set JWT subject "sub".
         *
         * @param value The JWT subject to set.
         *
         * @see <a href="https://tools.ietf.org/html/rfc7519#section-4.1.2">RFC-7519</a>
         *
         * @return  The [Builder] instance.
         */
        fun setSubject(value: String) = this.apply {
            jwt.payload.putIfAbsent(Constants.SUBJECT, value.toJson())
        }

        /**
         * Set JWT audience "aud".
         *
         * @param values The JWT audience to set.
         *
         * @see <a href="https://tools.ietf.org/html/rfc7519#section-4.1.3">RFC-7519</a>
         *
         * @return  The [Builder] instance.
         */
        fun setAudience(vararg values: String) = this.apply {
            jwt.payload.putIfAbsent(Constants.AUDIENCE, values.toList().toJson())
        }

        /**
         * Set JWT expires at "exp".
         *
         * @param value The JWT expires at to set.
         *
         * @see <a href="https://tools.ietf.org/html/rfc7519#section-4.1.4">RFC-7519</a>
         *
         * @return  The [Builder] instance.
         */
        fun setExpiresAt(value: Date) = this.apply {
            jwt.payload.putIfAbsent(Constants.EXPIRES_AT, value.toJson())
        }

        /**
         * Set JWT expires at "exp".
         *
         * @param value The JWT expires at to set.
         *
         * @see <a href="https://tools.ietf.org/html/rfc7519#section-4.1.4">RFC-7519</a>
         *
         * @return  The [Builder] instance.
         */
        fun setExpiresAt(value: LocalDateTime) = this.apply {
            jwt.payload.putIfAbsent(Constants.EXPIRES_AT, value.toJson())
        }

        /**
         * Set JWT not before "nbf".
         *
         * @param value The JWT not before to set.
         *
         * @see <a href="https://tools.ietf.org/html/rfc7519#section-4.1.5">RFC-7519</a>
         *
         * @return  The [Builder] instance.
         */
        fun setNotBefore(value: Date) = this.apply {
            jwt.payload.putIfAbsent(Constants.NOT_BEFORE, value.toJson())
        }

        /**
         * Set JWT not before "nbf".
         *
         * @param value The JWT not before to set.
         *
         * @see <a href="https://tools.ietf.org/html/rfc7519#section-4.1.5">RFC-7519</a>
         *
         * @return  The [Builder] instance.
         */
        fun setNotBefore(value: LocalDateTime) = this.apply {
            jwt.payload.putIfAbsent(Constants.NOT_BEFORE, value.toJson())
        }

        /**
         * Set JWT issued at "iat".
         *
         * @param value The JWT issued at to set.
         *
         * @see <a href="https://tools.ietf.org/html/rfc7519#section-4.1.6">RFC-7519</a>
         *
         * @return  The [Builder] instance.
         */
        fun setIssuedAt(value: Date) = this.apply {
            jwt.payload.putIfAbsent(Constants.ISSUED_AT, value.toJson())
        }

        /**
         * Set JWT issued at "iat" from epoch second.
         *
         * @param value The JWT issued at to set.
         *
         * @see <a href="https://tools.ietf.org/html/rfc7519#section-4.1.6">RFC-7519</a>
         *
         * @return  The [Builder] instance.
         */
        fun setIssuedAt(epochSecond: Long) = this.apply {
            jwt.payload.putIfAbsent(Constants.ISSUED_AT, epochSecond.toJson())
        }

        /**
         * Set JWT issued at "iat".
         *
         * @param value The JWT issued at to set.
         *
         * @see <a href="https://tools.ietf.org/html/rfc7519#section-4.1.6">RFC-7519</a>
         *
         * @return  The [Builder] instance.
         */
        fun setIssuedAt(value: LocalDateTime) = this.apply {
            jwt.payload.putIfAbsent(Constants.ISSUED_AT, value.toJson())
        }

        /**
         * Set JWT ID "jti".
         *
         * @param value The JWT ID to set.
         *
         * @see <a href="https://tools.ietf.org/html/rfc7519#section-4.1.7">RFC-7519</a>
         *
         * @return  The [Builder] instance.
         */
        fun setJwtId(value: String) = this.apply {
            jwt.payload.putIfAbsent(Constants.JWT_ID, value.toJson())
        }

        /**
         * Set JWT type "alg".
         *
         * @param value The JWT algorithm to set. Defaults to the provided [Algorithm] if none set.
         *
         * @see <a href="https://tools.ietf.org/html/rfc7519#section-5.1">RFC-7519</a>
         *
         * @return  The [Builder] instance.
         */
        fun setAlgorithm(value: String) = this.apply {
            jwt.header.putIfAbsent(Constants.ALGORITHM, value.toJson())
        }

        /**
         * Set JWT type "typ".
         *
         * @param value The JWT type to set.
         *
         * @see <a href="https://tools.ietf.org/html/rfc7519#section-5.1">RFC-7519</a>
         *
         * @return  The [Builder] instance.
         */
        fun setType(value: String) = this.apply {
            jwt.header.putIfAbsent(Constants.TYPE, value.toJson())
        }

        /**
         * Set JWT content type "cty".
         *
         * @param value The JWT type to set.
         *
         * @see <a href="https://tools.ietf.org/html/rfc7519#section-5.2">RFC-7519</a>
         *
         * @return  The [Builder] instance.
         */
        fun setContentType(value: String) = this.apply {
            jwt.header.putIfAbsent(Constants.CONTENT_TYPE, value.toJson())
        }

        /**
         * Signs the generated header and payload.
         *
         * @param algorithm The [Algorithm] to sign JWT with.
         *
         * @return  The generated JWT.
         */
        fun sign(algorithm: Algorithm): String {
            return jwt.sign(algorithm)
        }

    }

}