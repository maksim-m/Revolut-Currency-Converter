package me.maxdev.currencyconverter.app.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import me.maxdev.currencyconverter.api.CurrencyRatesApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

private const val BASE_URL = "https://revolut.duckdns.org/"


val networkModule = module {

    single<Moshi> { provideMoshi() }
    single<Retrofit> { provideRetrofit(get()) }
    single<CurrencyRatesApi> { provideCurrencyRatesApi(get()) }
}

private fun provideCurrencyRatesApi(retrofit: Retrofit): CurrencyRatesApi {
    return retrofit.create(CurrencyRatesApi::class.java)
}

private fun provideRetrofit(moshi: Moshi): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}

private fun provideMoshi(): Moshi {
    return Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
        .build()
}
