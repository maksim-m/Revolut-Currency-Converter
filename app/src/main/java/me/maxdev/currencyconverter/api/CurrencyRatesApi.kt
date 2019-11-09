package me.maxdev.currencyconverter.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyRatesApi {

    @GET("latest")
    suspend fun getRates(@Query("base") base: String?): Response<RatesResponse>
}
