package me.maxdev.currencyconverter.ui.currencyconverter

import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.maxdev.currencyconverter.databinding.ItemCurrencyRateBinding

class ViewHolder private constructor(
    private val binding: ItemCurrencyRateBinding,
    private val textWatcher: TextWatcher
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(viewModel: CurrencyConverterViewModel, item: CurrencyRateItem) {
        binding.editText.removeTextChangedListener(textWatcher)
        binding.vm = viewModel
        binding.currencyrateitem = item
        binding.executePendingBindings()
        if (item.editable) {
            binding.editText.addTextChangedListener(textWatcher)
        }
    }

    fun bindAmount(amount: Double) {
        val textToSet = CurrencyRateValueConverter.doubleToString(amount)
        if (binding.editText.text.toString() != textToSet) {
            val selectionStart = binding.editText.selectionStart
            binding.editText.setText(textToSet)
            if (selectionStart <= textToSet.length) {
                binding.editText.setSelection(selectionStart)
            } else {
                binding.editText.setSelection(textToSet.length)
            }
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
