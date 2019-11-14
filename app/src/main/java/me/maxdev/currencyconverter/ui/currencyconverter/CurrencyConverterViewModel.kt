package me.maxdev.currencyconverter.ui.currencyconverter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import me.maxdev.currencyconverter.api.RatesResponse
import me.maxdev.currencyconverter.rates.CurrencyRatesRepository

class CurrencyConverterViewModel(private val ratesRepository: CurrencyRatesRepository) :
    ViewModel() {

    companion object {
        private val DEFAULT_BASE = CurrencyRateItem("EUR", "10")
    }

    private val _rates = MutableLiveData<List<CurrencyRateItem>>(emptyList())
    val rates: LiveData<List<CurrencyRateItem>> = _rates

    private val baseObs: BehaviorSubject<CurrencyRateItem> =
        BehaviorSubject.createDefault(DEFAULT_BASE)

    private val disposable: CompositeDisposable = CompositeDisposable()

    private fun subscribeToRates() {
        Log.e("xxx", "subscribeToRates")
        baseObs
            .subscribeOn(Schedulers.io())
            .switchMap { base -> ratesRepository.getCurrencyRates(base.name) }
            .map { ratesResponse: RatesResponse -> getCurrencyRateItems(ratesResponse) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { items ->
                    _rates.value = getCurrencyRateItemsWithBase(items)
                },
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

    fun onItemClicked(item: CurrencyRateItem) {
        Log.e("xxx", "onItemClicked $item")
        baseObs.onNext(item)
    }

    fun onEditTextClicked(item: CurrencyRateItem) {
        Log.e("xxx", "onEditTextClicked $item")
        baseObs.onNext(item)
    }

    private fun getCurrencyRateItemsWithBase(items: List<CurrencyRateItem>?): List<CurrencyRateItem> {
        return mutableListOf(baseObs.value!!).apply {
            if (items != null) {
                addAll(items)
            }
        }
    }

    private fun getCurrencyRateItems(ratesResponse: RatesResponse): List<CurrencyRateItem> {
        return ratesResponse.rates.entries.map { entry: Map.Entry<String, Double> ->
            CurrencyRateItem(entry.key, entry.value.toString())
        }
    }
}
