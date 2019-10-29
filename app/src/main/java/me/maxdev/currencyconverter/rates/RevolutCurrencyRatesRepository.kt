package me.maxdev.currencyconverter.rates

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import me.maxdev.currencyconverter.data.RatesResponse
import me.maxdev.currencyconverter.data.Result

class RevolutCurrencyRatesRepository(private val dispatcher: CoroutineDispatcher = Dispatchers.Default) :
    CurrencyRatesRepository {
    override suspend fun getCurrencyRates(): Result<RatesResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
