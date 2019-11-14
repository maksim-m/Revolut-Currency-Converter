package me.maxdev.currencyconverter.ui

import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import me.maxdev.currencyconverter.ui.currencyconverter.CurrencyRateItem
import me.maxdev.currencyconverter.ui.currencyconverter.CurrencyRatesAdapter

@BindingAdapter("app:items")
fun setItems(recyclerView: RecyclerView, items: List<CurrencyRateItem>) {
    (recyclerView.adapter as CurrencyRatesAdapter).submitList(items)
}

@BindingAdapter("app:on_focus_change")
fun setOnFocusChange(editText: EditText, action: () -> Unit) {
    editText.setOnFocusChangeListener { v, hasFocus ->
        if (hasFocus) {
            action.invoke()
        }
    }
}
