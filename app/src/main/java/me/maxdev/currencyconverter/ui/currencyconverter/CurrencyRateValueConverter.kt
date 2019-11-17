package me.maxdev.currencyconverter.ui.currencyconverter

import java.text.NumberFormat
import java.util.*

object CurrencyRateValueConverter {

    @JvmStatic
    fun doubleToString(value: Double): String {
        val numberFormat = NumberFormat.getInstance(Locale.US)
        return numberFormat.format(value)
    }


    fun stringToDouble(value: String): Double {
        val numberFormat = NumberFormat.getInstance(Locale.US)
        return numberFormat.parse(value)?.toDouble() ?: 0.0
    }

}

