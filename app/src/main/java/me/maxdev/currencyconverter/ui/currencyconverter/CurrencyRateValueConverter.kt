package me.maxdev.currencyconverter.ui.currencyconverter

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.ParseException

object CurrencyRateValueConverter {

    @JvmStatic
    fun doubleToString(value: Double): String {
        return getDecimalFormat().format(value)
    }

    fun stringToDouble(value: String): Double {
        return try {
            getDecimalFormat().parse(value)?.toDouble() ?: 0.0
        } catch (ex: ParseException) {
            0.0
        }
    }

    private fun getDecimalFormat(): DecimalFormat {
        val decimalFormatSymbols = DecimalFormatSymbols().apply {
            decimalSeparator = '.'
        }
        return DecimalFormat().apply {
            setDecimalFormatSymbols(decimalFormatSymbols)
            minimumFractionDigits = 0
            maximumFractionDigits = 2
            isGroupingUsed = false
        }
    }

}

