package me.maxdev.currencyconverter.data

import java.util.*


data class RatesResponse(
    private val base: String,
    private val date: Date,
    private val rates: Map<String, Double>
)
