package me.maxdev.currencyconverter.ui.currencyconverter

import com.google.common.truth.Truth.assertThat
import org.junit.Test


class CurrencyRateValueConverterTest {

    @Test
    fun `format double values`() {
        assertThat(CurrencyRateValueConverter.doubleToString(123.45678)).matches("123.46")
        assertThat(CurrencyRateValueConverter.doubleToString(123.1)).matches("123.1")
    }

    @Test
    fun `parse double values from string`() {
        assertThat(CurrencyRateValueConverter.stringToDouble("123.45")).isWithin(0.01).of(123.45)
    }

    @Test
    fun `parse invalid double values from string`() {
        assertThat(CurrencyRateValueConverter.stringToDouble("")).isWithin(0.01).of(0.0)
        assertThat(CurrencyRateValueConverter.stringToDouble(".,.,.0")).isWithin(0.01).of(0.0)
    }
}
