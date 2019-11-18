package me.maxdev.currencyconverter.rates

import io.reactivex.Observable
import me.maxdev.currencyconverter.api.CurrencyRatesApi
import me.maxdev.currencyconverter.api.RatesResponse
import timber.log.Timber
import java.util.concurrent.TimeUnit

class RevolutCurrencyRatesRepository(
    private val currencyRatesApi: CurrencyRatesApi
) :
    CurrencyRatesRepository {

    override fun getCurrencyRates(base: String): Observable<RatesResponse> =
        Observable.interval(0, 1, TimeUnit.SECONDS)
            .flatMapSingle { currencyRatesApi.getRates(base) }
            .doOnNext { Timber.d("request $base") }

}
