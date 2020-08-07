package com.brendangoldberg.kotlin_jwt

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.JsonObject
import java.util.*

data class KtJwt(
    // Header
    var alg: String? = null,
    var type: String? = null,
    var contentType: String? = null,

    // Payload
    var issuer: String? = null,
    var subject: String? = null,
    var audience: List<String>? = null,
    var expiresAt: Date? = null,
    var notBefore: Date? = null,
    var issuedAt: Date? = null,
    var jwtId: String? = null
) {

    companion object {
        @JvmField
        internal val json = Utils.JSON
    }

    var header: JsonObject = JsonObject(emptyMap())
    var payload: JsonObject = JsonObject(emptyMap())

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

}