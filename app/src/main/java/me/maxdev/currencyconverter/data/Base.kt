package me.maxdev.currencyconverter.data

data class Base(val amount: Double, val currencyCode: String) {

    fun toBaseCurrencyRateItem() =
        CurrencyRateItem(currencyCode, amount, editable = true)

}
