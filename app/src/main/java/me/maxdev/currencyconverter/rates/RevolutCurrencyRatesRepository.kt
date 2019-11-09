package me.maxdev.currencyconverter.rates

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.maxdev.currencyconverter.api.CurrencyRatesApi
import me.maxdev.currencyconverter.api.RatesResponse
import me.maxdev.currencyconverter.data.Result
import java.io.IOException

class RevolutCurrencyRatesRepository(
    private val currencyRatesApi: CurrencyRatesApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    CurrencyRatesRepository {

    override suspend fun getCurrencyRates(): Result<RatesResponse> = withContext(dispatcher) {
        val result = currencyRatesApi.getRates(null)

        if (result.isSuccessful && result.body() != null) {
            return@withContext Result.Success(
                RatesResponse(
                    "EUR",
                    result.body()!!.date,
                    result.body()!!.rates
                )
            )
        } else {
            return@withContext Result.Error(IOException(result.errorBody()?.string()))
        }
    }

}
