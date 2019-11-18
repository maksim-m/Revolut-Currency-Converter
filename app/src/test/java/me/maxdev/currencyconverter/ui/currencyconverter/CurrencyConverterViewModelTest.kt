package me.maxdev.currencyconverter.ui.currencyconverter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.maxdev.currencyconverter.api.RatesResponse
import me.maxdev.currencyconverter.data.CurrencyRateItem
import me.maxdev.currencyconverter.data.CurrencyRatesRepository
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import java.util.*


@ExperimentalCoroutinesApi
class CurrencyConverterViewModelTest {
    private lateinit var mockRepository: CurrencyRatesRepository

    private lateinit var viewModel: CurrencyConverterViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        mockRepository = mock {
        }
        viewModel = CurrencyConverterViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    @Test
    fun `load currencies from repository`() {
        whenever(mockRepository.getCurrencyRates("EUR")).thenReturn(
            Observable.just(
                RatesResponse(
                    "EUR",
                    Date(),
                    hashMapOf("USD" to 1.2345)
                )
            )
        )

        viewModel.onResume()

        assertThat(viewModel.rates.value).containsExactly(
            CurrencyRateItem("EUR", 10.0, editable = true),
            CurrencyRateItem("USD", 10.0 * 1.2345)
        )
        assertThat(viewModel.showError.value).isFalse()
    }

    @Test
    fun `change base currency`() {
        whenever(mockRepository.getCurrencyRates("RUB")).thenReturn(
            Observable.just(
                RatesResponse(
                    "RUB",
                    Date(),
                    hashMapOf("USD" to 1.2345)
                )
            )
        )
        whenever(mockRepository.getCurrencyRates("EUR")).thenReturn(
            Observable.just(
                RatesResponse(
                    "EUR",
                    Date(),
                    hashMapOf("USD" to 1.2345)
                )
            )
        )

        viewModel.onResume()
        viewModel.onItemClicked(CurrencyRateItem("RUB", 20.0))

        assertThat(viewModel.rates.value).containsExactly(
            CurrencyRateItem("RUB", 20.0, editable = true),
            CurrencyRateItem("USD", 20.0 * 1.2345)
        )
        assertThat(viewModel.showError.value).isFalse()
    }

    @Test
    fun `change base amount`() {
        whenever(mockRepository.getCurrencyRates("RUB")).thenReturn(
            Observable.just(
                RatesResponse(
                    "RUB",
                    Date(),
                    hashMapOf("USD" to 1.2345)
                )
            )
        )
        whenever(mockRepository.getCurrencyRates("EUR")).thenReturn(
            Observable.just(
                RatesResponse(
                    "EUR",
                    Date(),
                    hashMapOf("USD" to 1.2345)
                )
            )
        )

        viewModel.onResume()
        viewModel.onBaseAmountChanged("100")

        assertThat(viewModel.rates.value).containsExactly(
            CurrencyRateItem("EUR", 100.0, editable = true),
            CurrencyRateItem("USD", 100.0 * 1.2345)
        )
        assertThat(viewModel.showError.value).isFalse()
    }

    @Test
    fun `show error message when no currency rates loaded`() {
        whenever(mockRepository.getCurrencyRates(any<String>())).thenReturn(
            Observable.error(IOException("Ooops!"))
        )

        viewModel.onResume()

        assertThat(viewModel.showError.value).isTrue()
    }

    @Test
    fun `remove error message when currencies are loaded after first error`() {
        whenever(mockRepository.getCurrencyRates(any<String>())).thenReturn(
            Observable.error(IOException("Ooops!"))
        )

        viewModel.onResume()

        assertThat(viewModel.showError.value).isTrue()

        whenever(mockRepository.getCurrencyRates(any<String>())).thenReturn(
            Observable.just(
                RatesResponse(
                    "RUB",
                    Date(),
                    hashMapOf("USD" to 1.2345)
                )
            )
        )

        viewModel.onResume()

        assertThat(viewModel.showError.value).isFalse()
    }

}
