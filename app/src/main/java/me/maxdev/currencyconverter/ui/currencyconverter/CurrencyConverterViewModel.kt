package me.maxdev.currencyconverter.ui.currencyconverter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import me.maxdev.currencyconverter.api.RatesResponse
import me.maxdev.currencyconverter.rates.CurrencyRatesRepository

class CurrencyConverterViewModel(private val ratesRepository: CurrencyRatesRepository) :
    ViewModel() {

    private val _rates = MutableLiveData<List<CurrencyRateItem>>(emptyList())
    val rates: LiveData<List<CurrencyRateItem>> = _rates

    private val disposable: CompositeDisposable = CompositeDisposable()

    private fun subscribeToRates() {
        Log.e("xxx", "subscribeToRates")
        ratesRepository.getCurrencyRates()
            .subscribeOn(Schedulers.io())
            .map { ratesResponse: RatesResponse -> asdasd(ratesResponse) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { items -> _rates.value = items },
                { error -> Log.e("xxx", error.message!!) }
            ).addTo(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun onPause() {
        disposable.clear()
    }

    fun onResume() {
        subscribeToRates()
    }

    private fun asdasd(ratesResponse: RatesResponse): List<CurrencyRateItem> {
        return ratesResponse.rates.entries.map { entry: Map.Entry<String, Double> ->
            CurrencyRateItem(entry.key, entry.value.toString())
        }
    }
}
