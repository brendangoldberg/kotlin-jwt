package com.brendangoldberg.kotlin_jwt

import com.brendangoldberg.kotlin_jwt.ext.toDate
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import java.time.DateTimeException
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

class KtJwtDecoder {

    companion object {
        private val json = Utils.JSON
        private val decoder = Utils.DECODER
    }

    var issuer: String? = null
    var subject: String? = null
    var audience: List<String>? = null
    var expiresAt: Date? = null
    var notBefore: Date? = null
    var issuedAt: Date? = null
    var jwtId: String? = null

    // Header
    var alg: String? = null
    var type: String? = null
    var contentType: String? = null

    private var header: JsonObject = JsonObject(emptyMap())
    private var payload: JsonObject = JsonObject(emptyMap())
    private var signature: String? = null

    fun decode(jwt: String) = this.apply {
        clear()
        val parts = jwt.split(".")
        val strHeader = String(decoder.decode(parts[0]))
        val strPayload = String(decoder.decode(parts[1]))
        header = json.parseJson(strHeader).jsonObject
        payload = json.parseJson(strPayload).jsonObject
        signature = parts[2]
        unwrapHeader()
        unwrapPayload()
    }

    fun clear() {
        issuer = null
        subject = null
        audience = null
        expiresAt = null
        notBefore = null
        issuedAt = null
        jwtId = null
        alg = null
        type = null
        contentType = null
    }

    fun <T> getClaim(key: String, serializer: KSerializer<T>): T? {
        try {
            val item = payload[key]
            if (item != null) {
                return json.fromJson(serializer, item)
            }
            return null
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private fun unwrapHeader() {
        with(header) {
            alg = this[Constants.ALGORITHM]?.contentOrNull
            type = this[Constants.TYPE]?.contentOrNull
            contentType = this[Constants.CONTENT_TYPE]?.contentOrNull
        }
    }

    private fun unwrapPayload() {
        with(payload) {
            issuer = this[Constants.ISSUER]?.contentOrNull
            subject = this[Constants.SUBJECT]?.contentOrNull

            this[Constants.AUDIENCE]?.jsonArray?.let {
                val audiences = ArrayList<String>()
                for (item in it) {
                    item.contentOrNull?.let { t ->
                        audiences.add(t)
                    }
                }
                audience = audiences
            }

            this[Constants.EXPIRES_AT]?.contentOrNull?.let { text ->
                try {
                    expiresAt = LocalDateTime.parse(text).toDate()
                } catch (e: DateTimeException) {
                    try {
                        expiresAt = Date.from(Instant.parse(text))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            this[Constants.NOT_BEFORE]?.contentOrNull?.let { text ->
                try {
                    notBefore = LocalDateTime.parse(text).toDate()
                } catch (e: DateTimeException) {
                    try {
                        notBefore = Date.from(Instant.parse(text))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            this[Constants.ISSUED_AT]?.contentOrNull?.let { text ->
                try {
                    issuedAt = LocalDateTime.parse(text).toDate()
                } catch (e: DateTimeException) {
                    try {
                        issuedAt = Date.from(Instant.parse(text))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            jwtId = this[Constants.JWT_ID]?.contentOrNull
        }
    }

}