package com.brendangoldberg.kotlin_jwt.serializers

import kotlinx.serialization.*
import java.time.Instant
import java.util.*

/**
 * [java.util.Date] KSerializer.
 */
object DateSerializer : KSerializer<Date> {

    override val descriptor: SerialDescriptor = PrimitiveDescriptor(Date::class.java.name, PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Date) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): Date {
        return Date.from(Instant.parse(decoder.decodeString()))
    }
}