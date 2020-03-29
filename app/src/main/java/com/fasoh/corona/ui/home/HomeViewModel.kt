package com.fasoh.corona.ui.home

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fasoh.corona.models.BaseStatisticItem
import com.fasoh.corona.models.country.CountryDataItem
import com.fasoh.corona.models.global.GlobalStatisticsItem
import com.fasoh.corona.ui.BaseViewModel
import com.fasoh.corona.usecases.GetCountryStatisticsUseCase
import com.fasoh.corona.usecases.GetCountryStatisticsUseCaseCallback
import com.fasoh.corona.usecases.GetGlobalStatisticsUseCase
import com.fasoh.corona.usecases.GetGlobalStatisticsUseCaseCallback

class HomeViewModel constructor(
    private val getGlobalStatisticsUseCase: GetGlobalStatisticsUseCase,
    private val getCountryStatisticsUseCase: GetCountryStatisticsUseCase
) :
    BaseViewModel() {

    fun setup(lifecycle: Lifecycle) {
        autoDisposable.bindTo(lifecycle)
        getGlobalData()
    }

    private val _globalStatistics = MutableLiveData<BaseStatisticItem>()
    val globalStatisticsItem: LiveData<BaseStatisticItem> = _globalStatistics
    private val callback = object : GetGlobalStatisticsUseCaseCallback {
        override fun onSuccess(globalStatisticsItem: GlobalStatisticsItem) {
            _globalStatistics.postValue(globalStatisticsItem)
        }

    }

    private val countryStatisticsCallback = object : GetCountryStatisticsUseCaseCallback{
        override fun onSuccess(countryDataItem: CountryDataItem) {
            _globalStatistics.postValue(countryDataItem)
        }

    }
    fun getCountryData(code: String){
        getCountryStatisticsUseCase.execute(code,countryStatisticsCallback,autoDisposable)
    }

    fun getGlobalData(){
        getGlobalStatisticsUseCase.execute(callback, autoDisposable)
    }
}