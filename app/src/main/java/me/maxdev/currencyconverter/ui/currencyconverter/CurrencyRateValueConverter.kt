package me.maxdev.currencyconverter.ui.currencyconverter

import java.text.NumberFormat
import java.text.ParseException
import java.util.*

object CurrencyRateValueConverter {

    @JvmStatic
    fun doubleToString(value: Double): String {
        val numberFormat = NumberFormat.getInstance(Locale.UK)
        return numberFormat.format(value)
    }

    fun stringToDouble(value: String): Double {
        val numberFormat = NumberFormat.getInstance(Locale.UK)
        return try {
            numberFormat.parse(value)?.toDouble() ?: 0.0
        } catch (ex: ParseException) {
            0.0
        }
    }

}

