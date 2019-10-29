package me.maxdev.currencyconverter.ui.currencyconverter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import me.maxdev.currencyconverter.data.RatesResponse
import me.maxdev.currencyconverter.data.Result
import me.maxdev.currencyconverter.rates.CurrencyRatesRepository
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

@ExperimentalCoroutinesApi
class CurrencyConverterViewModelTest {

    private val mainThreadSurrogate = Dispatchers.Unconfined

    private lateinit var mockRepository: CurrencyRatesRepository

    private lateinit var viewModel: CurrencyConverterViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)

        mockRepository = mock {
        }
        viewModel = CurrencyConverterViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `show empty list after initialization`() = runBlockingTest {
        assertThat(viewModel.rates.value).isEmpty()
    }

    @Test
    fun `load items from repository`() = runBlockingTest {
        whenever(mockRepository.getCurrencyRates()).thenReturn(
            Result.Success(
                RatesResponse("EUR", Date(), hashMapOf("USD" to 1.2345))
            )
        )

        viewModel.loadRates()

        assertThat(viewModel.rates.value).containsExactly("USD")
    }
}
