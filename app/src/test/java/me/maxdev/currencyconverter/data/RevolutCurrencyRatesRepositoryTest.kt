package me.maxdev.currencyconverter.data

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import me.maxdev.currencyconverter.api.CurrencyRatesApi
import me.maxdev.currencyconverter.api.RatesResponse
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*
import java.util.concurrent.TimeUnit

class RevolutCurrencyRatesRepositoryTest {
    private lateinit var mockApi: CurrencyRatesApi

    private lateinit var repository: RevolutCurrencyRatesRepository

    private val testScheduler = TestScheduler()

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { testScheduler }

        mockApi = mock {

        }
        repository = RevolutCurrencyRatesRepository(mockApi)
    }

    @After
    fun tearDown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    @Test
    fun `request new rates every second`() {
        val observer = TestObserver<RatesResponse>()

        val response1 = RatesResponse(
            "USD",
            Date(),
            hashMapOf("EUR" to 1.2345)
        )

        whenever(mockApi.getRates(any())).thenReturn(
            Single.just(
                response1
            )
        )

        repository.getCurrencyRates("USD").subscribe(observer)
        observer.awaitCount(1)
        observer.assertValue(response1)

        val response2 = RatesResponse(
            "EUR",
            Date(),
            hashMapOf("USD" to 5.67)
        )
        whenever(mockApi.getRates(any())).thenReturn(
            Single.just(
                response2
            )
        )
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        observer.awaitCount(2)
        observer.assertValues(response1, response2)

    }
}
