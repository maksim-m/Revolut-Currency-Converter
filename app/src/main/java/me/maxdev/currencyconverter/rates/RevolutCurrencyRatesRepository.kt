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

    override fun getCurrencyRates(base: String): Observable<RatesResponse> =
        Observable.interval(0, 1, TimeUnit.SECONDS)
            .flatMapSingle { number -> currencyRatesApi.getRates(base) }
            .doOnNext { Log.e("xxx", "request $base") }

}
