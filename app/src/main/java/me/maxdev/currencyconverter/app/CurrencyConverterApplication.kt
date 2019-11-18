package me.maxdev.currencyconverter.app

import android.app.Application
import me.maxdev.currencyconverter.BuildConfig
import me.maxdev.currencyconverter.app.di.appModule
import me.maxdev.currencyconverter.app.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class CurrencyConverterApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@CurrencyConverterApplication)
            modules(listOf(appModule, networkModule))
        }
    }

}
