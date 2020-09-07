package com.brendangoldberg.kotlin_jwt.serializers

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime

/**
 * [java.time.LocalDateTime] KSerializer.
 */
object LocalDateTimeSerializer : KSerializer<LocalDateTime> {

    override val descriptor = PrimitiveSerialDescriptor(LocalDateTime::class.java.name, PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        return LocalDateTime.parse(decoder.decodeString())
    }

}