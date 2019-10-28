package me.maxdev.currencyconverter.rates

import me.maxdev.currencyconverter.data.RatesResponse
import me.maxdev.currencyconverter.data.Result

class RevolutCurrencyRatesRepository : CurrencyRatesRepository {
    override suspend fun getCurrencyRates(): Result<RatesResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
