package me.maxdev.currencyconverter.app.di

import me.maxdev.currencyconverter.rates.CurrencyRatesRepository
import me.maxdev.currencyconverter.rates.RevolutCurrencyRatesRepository
import me.maxdev.currencyconverter.ui.currencyconverter.CurrencyConverterViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Modules {

    val appModule = module {

        single<CurrencyRatesRepository> { RevolutCurrencyRatesRepository() }

        viewModel { CurrencyConverterViewModel(get()) }
    }
}
