package me.maxdev.currencyconverter.ui.currencyconverter

import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import me.maxdev.currencyconverter.data.CurrencyRateItem

class CurrencyRatesAdapter(private val viewModel: CurrencyConverterViewModel) :
    ListAdapter<CurrencyRateItem, ViewHolder>(ItemDiffCallback()) {

    private val textWatcher = object : TextWatcher {

        override fun afterTextChanged(editable: Editable) {
            viewModel.onBaseAmountChanged(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, textWatcher)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.bindAmount(payloads[0] as Double)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(viewModel, getItem(position))
    }

}

class ItemDiffCallback : DiffUtil.ItemCallback<CurrencyRateItem>() {
    override fun areItemsTheSame(oldItem: CurrencyRateItem, newItem: CurrencyRateItem): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: CurrencyRateItem, newItem: CurrencyRateItem): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: CurrencyRateItem, newItem: CurrencyRateItem): Any? {
        return newItem.value
    }
}
