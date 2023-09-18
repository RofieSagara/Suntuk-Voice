package com.indev.suntuk.utils

import java.time.Instant

fun Long.toInstant(): Instant {
    return Instant.ofEpochMilli(this)
}