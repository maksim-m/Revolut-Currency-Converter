package me.maxdev.currencyconverter.ui.currencyconverter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.maxdev.currencyconverter.databinding.ItemCurrencyRateBinding

class CurrencyRatesAdapter(private val viewModel: CurrencyConverterViewModel) :
    ListAdapter<CurrencyRateItem, CurrencyRatesAdapter.ViewHolder>(TaskDiffCallback()) {

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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(viewModel, getItem(position))
    }

    class ViewHolder private constructor(
        private val binding: ItemCurrencyRateBinding,
        private val textWatcher: TextWatcher
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: CurrencyConverterViewModel, item: CurrencyRateItem) {
            binding.vm = viewModel
            binding.currencyrateitem = item
            binding.executePendingBindings()
            if (item.editable) {
                binding.editText.addTextChangedListener(textWatcher)
            }
        }

        companion object {
            fun from(parent: ViewGroup, textWatcher: TextWatcher): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCurrencyRateBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding, textWatcher)
            }
        }
    }

}

class TaskDiffCallback : DiffUtil.ItemCallback<CurrencyRateItem>() {
    override fun areItemsTheSame(oldItem: CurrencyRateItem, newItem: CurrencyRateItem): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: CurrencyRateItem, newItem: CurrencyRateItem): Boolean {
        return oldItem == newItem
    }
}
