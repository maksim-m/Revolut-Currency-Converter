package me.maxdev.currencyconverter.rates

import android.util.Log
import io.reactivex.Observable
import me.maxdev.currencyconverter.api.CurrencyRatesApi
import me.maxdev.currencyconverter.api.RatesResponse
import java.util.concurrent.TimeUnit

class RevolutCurrencyRatesRepository(
    private val currencyRatesApi: CurrencyRatesApi
) :
    CurrencyRatesRepository {

    private companion object {
        private val TAG = RevolutCurrencyRatesRepository::class.java.simpleName
    }

    override fun getCurrencyRates(base: String): Observable<RatesResponse> =
        Observable.interval(0, 1, TimeUnit.SECONDS)
            .flatMapSingle { currencyRatesApi.getRates(base) }
            .doOnNext { Log.d(TAG, "request $base") }

}
