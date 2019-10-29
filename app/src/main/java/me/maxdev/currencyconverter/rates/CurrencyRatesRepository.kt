package me.maxdev.currencyconverter.rates

import me.maxdev.currencyconverter.data.RatesResponse
import me.maxdev.currencyconverter.data.Result

interface CurrencyRatesRepository {

    suspend fun getCurrencyRates(): Result<RatesResponse>
}