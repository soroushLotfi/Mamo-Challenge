package com.app.mamochallenge.models

data class FormattedNumber(
    val wholePart: String,
    val tenths: String,
    val hundredths: String,
    val isDecimalEnabled: Boolean
)
