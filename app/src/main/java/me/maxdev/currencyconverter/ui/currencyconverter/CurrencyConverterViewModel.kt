package me.maxdev.currencyconverter.ui.currencyconverter

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
import me.maxdev.currencyconverter.data.CurrencyRateItem
import me.maxdev.currencyconverter.data.CurrencyRatesRepository
import timber.log.Timber


class CurrencyConverterViewModel(private val ratesRepository: CurrencyRatesRepository) :
    ViewModel() {

    companion object {
        private val DEFAULT_BASE = Base(10.0, "EUR")
    }

    private val _rates = MutableLiveData<List<CurrencyRateItem>>(emptyList())
    val rates: LiveData<List<CurrencyRateItem>> = _rates

    private val _showError = MutableLiveData<Boolean>(false)
    val showError: LiveData<Boolean> = _showError

    private val baseObservable: BehaviorSubject<Base> =
        BehaviorSubject.createDefault(DEFAULT_BASE)

    private val disposable: CompositeDisposable = CompositeDisposable()

    private fun subscribeToRates() {
        Observable.combineLatest(
            baseObservable.subscribeOn(Schedulers.io()),
            ratesObservable().subscribeOn(Schedulers.io()),
            BiFunction<Base, RatesResponse, List<CurrencyRateItem>> { base: Base, ratesResponse: RatesResponse ->
                combineBaseAndResponse(base, ratesResponse)
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { items ->
                    _showError.value = false
                    _rates.value = items
                },
                { error ->
                    if (rates.value.isNullOrEmpty()) {
                        _showError.value = true
                    }
                    Timber.e(error)
                }
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
        val currentBase = baseObservable.value!!
        val newBase = currentBase.copy(amount = item.value, currencyCode = item.name)
        if (newBase.currencyCode != currentBase.currencyCode) {
            Timber.d("new Base: $newBase")
            baseObservable.onNext(newBase)
        }
    }

    fun onBaseAmountChanged(newValue: String) {
        val currentBase = baseObservable.value!!
        val newBase =
            currentBase.copy(
                amount = CurrencyRateValueConverter.stringToDouble(newValue),
                currencyCode = currentBase.currencyCode
            )
        if (newBase != currentBase) {
            Timber.d("new Base: $newBase")
            baseObservable.onNext(newBase)
        }
    }


    private fun ratesObservable(): Observable<RatesResponse> {
        return baseObservable.switchMap { base -> ratesRepository.getCurrencyRates(base.currencyCode) }
    }

    private fun combineBaseAndResponse(base: Base, ratesResponse: RatesResponse): List<CurrencyRateItem> {
        val currencyRateItems = ratesResponse.rates.entries.map { entry: Map.Entry<String, Double> ->
            CurrencyRateItem(
                entry.key,
                entry.value * base.amount
            )
        }
        return mutableListOf(base.toBaseCurrencyRateItem()).apply {
            addAll(currencyRateItems)
        }
    }
}
