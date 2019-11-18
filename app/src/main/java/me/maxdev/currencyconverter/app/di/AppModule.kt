package me.maxdev.currencyconverter.app.di

import me.maxdev.currencyconverter.data.CurrencyRatesRepository
import me.maxdev.currencyconverter.data.RevolutCurrencyRatesRepository
import me.maxdev.currencyconverter.ui.currencyconverter.CurrencyConverterViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<CurrencyRatesRepository> {
        RevolutCurrencyRatesRepository(
            get()
        )
    }

    viewModel { CurrencyConverterViewModel(get()) }
}
