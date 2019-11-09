package me.maxdev.currencyconverter.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import me.maxdev.currencyconverter.ui.currencyconverter.CurrencyRateItem
import me.maxdev.currencyconverter.ui.currencyconverter.CurrencyRatesAdapter

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<CurrencyRateItem>) {
    (listView.adapter as CurrencyRatesAdapter).submitList(items)
}
