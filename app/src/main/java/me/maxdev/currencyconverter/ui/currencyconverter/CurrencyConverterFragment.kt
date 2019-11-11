package me.maxdev.currencyconverter.ui.currencyconverter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.maxdev.currencyconverter.databinding.CurrencyConverterFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class CurrencyConverterFragment : Fragment() {

    companion object {
        fun create() = CurrencyConverterFragment()
    }

    private val viewModel by viewModel<CurrencyConverterViewModel>()

    private lateinit var viewBinding: CurrencyConverterFragmentBinding

    private lateinit var adapter: CurrencyRatesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = CurrencyConverterFragmentBinding.inflate(inflater, container, false).apply {
            vm = viewModel
        }
        return viewBinding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewBinding.lifecycleOwner = this.viewLifecycleOwner
        val vm = viewBinding.vm
        if (vm != null) {
            adapter = CurrencyRatesAdapter(vm)
            viewBinding.ratesList.adapter = adapter
        }
    }
}
