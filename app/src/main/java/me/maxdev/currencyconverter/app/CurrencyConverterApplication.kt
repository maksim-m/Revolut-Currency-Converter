package me.maxdev.currencyconverter.app

import android.app.Application
import me.maxdev.currencyconverter.app.di.Modules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CurrencyConverterApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CurrencyConverterApplication)
            modules(Modules.appModule)
        }
    }

}
