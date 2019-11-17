@file:JvmName("Converter")

package me.maxdev.currencyconverter.ui.currencyconverter

import java.text.NumberFormat
import java.util.*


fun doubleToString(value: Double): String {
    val numberFormat = NumberFormat.getInstance(Locale.getDefault())
    return numberFormat.format(value)
}


fun stringToDouble(value: String): Double {
    val numberFormat = NumberFormat.getInstance(Locale.getDefault())
    return numberFormat.parse(value)?.toDouble() ?: 0.0
}
