package me.maxdev.currencyconverter.ui.currencyconverter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import me.maxdev.currencyconverter.api.RatesResponse
import me.maxdev.currencyconverter.data.Base
import me.maxdev.currencyconverter.rates.CurrencyRatesRepository


class CurrencyConverterViewModel(private val ratesRepository: CurrencyRatesRepository) :
    ViewModel() {

    companion object {
        private val DEFAULT_BASE = Base(10.0, "EUR")
    }

    private val _rates = MutableLiveData<List<CurrencyRateItem>>(emptyList())
    val rates: LiveData<List<CurrencyRateItem>> = _rates

    private val baseObservable: BehaviorSubject<Base> =
        BehaviorSubject.createDefault(DEFAULT_BASE)

    private val disposable: CompositeDisposable = CompositeDisposable()

    private fun subscribeToRates() {
        Log.e("xxx", "subscribeToRates")
        Observable.combineLatest(
            baseObservable,
            ratesObservable(),
            BiFunction<Base, RatesResponse, List<CurrencyRateItem>> { base: Base, ratesResponse: RatesResponse ->
                ratesResponse.rates.entries.map { entry: Map.Entry<String, Double> ->
                    CurrencyRateItem(entry.key, (entry.value * base.amount).toString())
                }
            })
            .map { items ->
                mutableListOf(baseObservable.value!!.toCurrencyRateItem()).apply {
                    addAll(items)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { items ->
                    _rates.value = items
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
        val currentBase = baseObservable.value!!
        val newBase = currentBase.copy(amount = item.rate.toDouble(), currencyCode = item.name)
        Log.e("xxx", "new Base: $newBase")
        baseObservable.onNext(newBase)
    }

    fun onEditTextClicked(item: CurrencyRateItem) {
        Log.e("xxx", "onEditTextClicked $item")
        // TODO

    }


    private fun ratesObservable(): Observable<RatesResponse> {
        return baseObservable.switchMap { base -> ratesRepository.getCurrencyRates(base.currencyCode) }
    }
}
