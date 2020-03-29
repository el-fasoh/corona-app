package com.fasoh.corona.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.fasoh.corona.CountryArrayAdapter
import com.fasoh.corona.R
import com.fasoh.corona.databinding.FragmentHomeBinding
import com.fasoh.corona.extentions.hide
import com.fasoh.corona.extentions.show
import com.google.gson.Gson
import com.heetch.countrypicker.Country
import com.heetch.countrypicker.Utils
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private val homeViewModel by viewModel<HomeViewModel>()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        homeViewModel.setup(lifecycle)

        setupPicker()

        homeViewModel.globalStatisticsItem.observe(viewLifecycleOwner, Observer {
            Timber.d("Item received: ${Gson().toJson(it)}")
            binding.statistic = it
            hideViews(getProgressBars(), true)
        })


        return binding.root
    }

    private fun setupPicker() {
        val isoCountryCodes: Array<String> = Locale.getISOCountries()
        val list = ArrayList<String>()
        list.add("ALL")
        isoCountryCodes.forEach {
            list.add(it)
        }

        val items = ArrayList<Country>()
        items.add(Country("ALL", "ALL"))

         items.addAll( Utils.parseCountries(Utils.getCountriesJSON(activity)))


        val countryAdapter = CountryArrayAdapter(activity!!, items)
        binding.numberPicker.adapter = countryAdapter
        binding.numberPicker.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(
                adapter: AdapterView<*>,
                p1: View?,
                position: Int,
                p3: Long
            ) {
                val country =adapter.selectedItem as Country
                hideViews(getProgressBars(), false)
                clearTextFields(getTextFields())
                when (country.isoCode) {
                    "ALL" -> homeViewModel.getGlobalData()
                    else -> homeViewModel.getCountryData(country.isoCode)
                }

            }
        }
    }

    private fun hideViews(views: List<View>, hide: Boolean) {
        views.forEach {
            if (hide)
                it.hide()
            else
                it.show()
        }
    }

    private fun clearTextFields(fields: List<TextView>) {
        fields.forEach {
            it.text = ""
        }
    }

    private fun getProgressBars() = listOf(
        binding.progressBar, binding.progressBar2, binding.progressBar3,
        binding.progressBar4, binding.progressBar5, binding.progressBar6
    )

    private fun getTextFields() = listOf(
        binding.totalDeaths, binding.totalInfections, binding.totalSerious,
        binding.totalToday, binding.totalUnResolved, binding.totalRecoveries
    )


}