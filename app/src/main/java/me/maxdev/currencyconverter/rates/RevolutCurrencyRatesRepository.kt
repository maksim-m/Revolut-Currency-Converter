package me.maxdev.currencyconverter.rates

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import me.maxdev.currencyconverter.data.RatesResponse
import me.maxdev.currencyconverter.data.Result
import java.util.*

class RevolutCurrencyRatesRepository(private val dispatcher: CoroutineDispatcher = Dispatchers.Default) :
    CurrencyRatesRepository {

    override suspend fun getCurrencyRates(): Result<RatesResponse> =
        Result.Success(RatesResponse("EUR", Date(), hashMapOf("USD" to 1.2345, "RUB" to 2.3456)))
}
