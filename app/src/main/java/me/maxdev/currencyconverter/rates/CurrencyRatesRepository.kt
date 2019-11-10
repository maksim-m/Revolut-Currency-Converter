package me.maxdev.currencyconverter.rates

import kotlinx.coroutines.flow.Flow
import me.maxdev.currencyconverter.api.RatesResponse
import me.maxdev.currencyconverter.data.Result

interface CurrencyRatesRepository {

    suspend fun getCurrencyRates(): Flow<RatesResponse>
}
