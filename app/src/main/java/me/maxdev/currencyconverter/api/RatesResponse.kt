package me.maxdev.currencyconverter.api

import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class RatesResponse(
    val base: String,
    val date: Date,
    val rates: Map<String, Double>
)
