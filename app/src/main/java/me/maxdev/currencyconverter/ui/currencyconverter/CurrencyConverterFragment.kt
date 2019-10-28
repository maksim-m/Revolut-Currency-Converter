package me.maxdev.currencyconverter.ui.currencyconverter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.maxdev.currencyconverter.R
import org.koin.android.viewmodel.ext.android.viewModel

class CurrencyConverterFragment : Fragment() {

    companion object {
        fun newInstance() = CurrencyConverterFragment()
    }

    val viewModel: CurrencyConverterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

}
