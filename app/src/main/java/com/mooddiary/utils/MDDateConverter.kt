package com.mooddiary.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val dateFormatterPattern = "yyyy-MM-dd"

fun LocalDate.toFormattedString(): String {
    val formatter = DateTimeFormatter.ofPattern(dateFormatterPattern)
    return format(formatter)
}

fun String.toLocalDateTime(): LocalDate {
    val formatter = DateTimeFormatter.ofPattern(dateFormatterPattern)
    return LocalDate.parse(this, formatter)
}