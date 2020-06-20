package com.brendangoldberg.kotlin_jwt.serializers

import kotlinx.serialization.*
import java.time.LocalDateTime

/**
 * [java.time.LocalDateTime] KSerializer.
 */
object LocalDateTimeSerializer : KSerializer<LocalDateTime> {

    override val descriptor: SerialDescriptor =
        PrimitiveDescriptor(LocalDateTime::class.java.name, PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        return LocalDateTime.parse(decoder.decodeString())
    }

}