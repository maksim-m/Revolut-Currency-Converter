package me.maxdev.currencyconverter.app.di

import me.maxdev.currencyconverter.rates.CurrencyRatesRepository
import me.maxdev.currencyconverter.rates.RevolutCurrencyRatesRepository
import me.maxdev.currencyconverter.ui.currencyconverter.CurrencyConverterViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<CurrencyRatesRepository> { RevolutCurrencyRatesRepository(get()) }

    viewModel { CurrencyConverterViewModel(get()) }
}
