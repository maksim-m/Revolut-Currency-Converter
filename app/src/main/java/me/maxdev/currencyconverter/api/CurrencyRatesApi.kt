package me.maxdev.currencyconverter.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyRatesApi {

    @GET("latest")
    fun getRates(@Query("base") base: String?): Single<RatesResponse>
}
