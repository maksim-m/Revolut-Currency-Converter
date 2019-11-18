package me.maxdev.currencyconverter.data

import io.reactivex.Observable
import me.maxdev.currencyconverter.api.RatesResponse

interface CurrencyRatesRepository {

    fun getCurrencyRates(base: String): Observable<RatesResponse>
}
