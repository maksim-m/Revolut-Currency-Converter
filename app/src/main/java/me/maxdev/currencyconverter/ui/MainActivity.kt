package me.maxdev.currencyconverter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.maxdev.currencyconverter.R
import me.maxdev.currencyconverter.ui.currencyconverter.CurrencyConverterFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CurrencyConverterFragment.create())
                .commitNow()
        }
    }

}
