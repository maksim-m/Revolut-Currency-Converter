package me.maxdev.currencyconverter.data

import java.util.*


data class RatesResponse(
    val base: String,
    val date: Date,
    val rates: Map<String, Double>
)
