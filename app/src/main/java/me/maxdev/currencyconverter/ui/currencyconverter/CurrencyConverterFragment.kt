package me.maxdev.currencyconverter.ui.currencyconverter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
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
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                Log.e("xxx", "onChanged")
                //viewBinding.ratesList.scrollToPosition(0)
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                Log.e("xxx", "onItemRangeRemoved")
            }

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                Log.e("xxx", "onItemRangeMoved")
                viewBinding.ratesList.scrollToPosition(0)
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                Log.e("xxx", "onItemRangeInserted")
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                Log.e("xxx", "onItemRangeChanged")
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                Log.e("xxx", "onItemRangeChanged")
            }
        })
    }
}
