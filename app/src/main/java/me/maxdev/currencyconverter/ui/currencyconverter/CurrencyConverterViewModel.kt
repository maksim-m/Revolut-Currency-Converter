package me.maxdev.currencyconverter.ui.currencyconverter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.maxdev.currencyconverter.data.Result
import me.maxdev.currencyconverter.rates.CurrencyRatesRepository

class CurrencyConverterViewModel(private val ratesRepository: CurrencyRatesRepository) :
    ViewModel() {

    private val _rates = MutableLiveData<List<CurrencyRateItem>>().apply { value = emptyList() }
    val rates: LiveData<List<CurrencyRateItem>> = _rates

    init {
        loadRates()
    }

    fun loadRates() {
        viewModelScope.launch {
            when (val result = ratesRepository.getCurrencyRates()) {
                is Result.Success -> {
                    _rates.value =
                        result.data.rates.entries.map { entry: Map.Entry<String, Double> ->
                            CurrencyRateItem(entry.key)
                        }
                }
                is Result.Error -> TODO()
            }
        }
    }
}
