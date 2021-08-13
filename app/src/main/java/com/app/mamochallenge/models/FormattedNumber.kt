package com.app.mamochallenge.models

data class FormattedNumber(
    val wholePart: String,
    val tenths: String,
    val hundredths: String,
    val hasPoint: Boolean,
    val hasTenths: Boolean,
    val hasHundredths: Boolean
) {
    val string get() = "$wholePart.$tenths$hundredths"
}
