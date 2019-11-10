package me.maxdev.currencyconverter.ui.currencyconverter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import me.maxdev.currencyconverter.api.RatesResponse
import me.maxdev.currencyconverter.rates.CurrencyRatesRepository

@ExperimentalCoroutinesApi
class CurrencyConverterViewModel(private val ratesRepository: CurrencyRatesRepository) :
    ViewModel() {

    private val _rates = MutableLiveData<List<CurrencyRateItem>>(emptyList())
    val rates: LiveData<List<CurrencyRateItem>> = _rates

    init {
        loadRates()
    }

    @UseExperimental(InternalCoroutinesApi::class)
    fun loadRates() {
        viewModelScope.launch {
            val a = ratesRepository.getCurrencyRates()
                .collect(object : FlowCollector<RatesResponse> {
                    override suspend fun emit(value: RatesResponse) {
                        _rates.value = value.rates.entries.map { entry: Map.Entry<String, Double> ->
                            CurrencyRateItem(entry.key, entry.value.toString())
                        }
                    }
                })
        }
    }
}
