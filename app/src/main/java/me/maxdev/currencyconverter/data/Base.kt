package me.maxdev.currencyconverter.data

import me.maxdev.currencyconverter.ui.currencyconverter.CurrencyRateItem

data class Base(val amount: Double, val currencyCode: String) {

    fun toBaseCurrencyRateItem() = CurrencyRateItem(currencyCode, amount, editable = true)

}
