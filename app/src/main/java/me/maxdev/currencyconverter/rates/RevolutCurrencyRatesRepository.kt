package me.maxdev.currencyconverter.rates

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.maxdev.currencyconverter.api.CurrencyRatesApi
import me.maxdev.currencyconverter.api.RatesResponse
import me.maxdev.currencyconverter.data.Result
import java.io.IOException

class RevolutCurrencyRatesRepository(
    private val currencyRatesApi: CurrencyRatesApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    CurrencyRatesRepository {

    override suspend fun getCurrencyRates(): Flow<RatesResponse> = flow {
        while (true) {
            val result = requestRates()
            if (result is Result.Success) {
                emit(result.data)
                Log.e("xxx", "emit")
            }
            delay(1000)
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun requestRates(): Result<RatesResponse> {
        val result = currencyRatesApi.getRates(null)

        return if (result.isSuccessful && result.body() != null) {
            Result.Success(
                RatesResponse(
                    "EUR",
                    result.body()!!.date,
                    result.body()!!.rates
                )
            )
        } else {
            Result.Error(IOException(result.errorBody()?.string()))
        }

    }

}
