@file:JvmName("Converter")

package me.maxdev.currencyconverter.ui.currencyconverter

import java.util.*


fun doubleToString(value: Double): String {
    return String.format(Locale.getDefault(), "%.2f", value)
}


fun stringToDouble(value: String): Double {
    return value.toDouble()
}
