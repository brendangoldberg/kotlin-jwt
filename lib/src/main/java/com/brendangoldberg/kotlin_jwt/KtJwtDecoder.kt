package com.brendangoldberg.kotlin_jwt

import com.brendangoldberg.kotlin_jwt.KtJwtDecoder.json
import com.brendangoldberg.kotlin_jwt.ext.toDate
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*
import kotlinx.serialization.parse
import java.time.DateTimeException
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

object KtJwtDecoder {

    @JvmField
    internal val json = Utils.JSON

    @JvmField
    internal val decoder = Utils.DECODER

    @JvmStatic
    fun decode(jwt: String): KtJwt {
        val parts = jwt.split(".")
        val strHeader = String(decoder.decode(parts[0]))
        val strPayload = String(decoder.decode(parts[1]))
        val header = json.parseToJsonElement(strHeader).jsonObject
        val payload = json.parseToJsonElement(strPayload).jsonObject

        return KtJwt().apply {
            this.header = header
            this.payload = payload

            with(header) {
                alg = this[Constants.ALGORITHM]?.jsonPrimitive?.contentOrNull
                type = this[Constants.TYPE]?.jsonPrimitive?.contentOrNull
                contentType = this[Constants.CONTENT_TYPE]?.jsonPrimitive?.contentOrNull
            }
            with(payload) {
                issuer = this[Constants.ISSUER]?.jsonPrimitive?.contentOrNull
                subject = this[Constants.SUBJECT]?.jsonPrimitive?.contentOrNull

                this[Constants.AUDIENCE]?.jsonArray?.let {
                    val audiences = ArrayList<String>()
                    for (item in it) {
                        item.jsonPrimitive.contentOrNull?.let { t ->
                            audiences.add(t)
                        }
                    }
                    audience = audiences
                }

                this[Constants.EXPIRES_AT]?.jsonPrimitive?.contentOrNull?.let { text ->
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

                this[Constants.NOT_BEFORE]?.jsonPrimitive?.contentOrNull?.let { text ->
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

                this[Constants.ISSUED_AT]?.jsonPrimitive?.contentOrNull?.let { text ->
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

                jwtId = this[Constants.JWT_ID]?.jsonPrimitive?.contentOrNull
            }
        }
    }

}