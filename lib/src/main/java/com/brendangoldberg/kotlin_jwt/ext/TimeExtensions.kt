package com.brendangoldberg.kotlin_jwt.ext

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

internal fun LocalDateTime.toDate(): Date {
    return Date.from(this.atZone(ZoneId.systemDefault()).toInstant())
}