package com.fasoh.corona.ui.globe

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fasoh.corona.models.timeline.TimelineDataItem
import com.fasoh.corona.ui.BaseViewModel
import com.fasoh.corona.usecases.GetDistinctDailyWorldStatisticsCallback
import com.fasoh.corona.usecases.GetDistinctDailyWorldStatisticsUseCase
import java.text.SimpleDateFormat
import java.util.*

class GeoChartViewModel(private val getDistinctDailyWorldStatisticsUseCase: GetDistinctDailyWorldStatisticsUseCase) :
    BaseViewModel(), GetDistinctDailyWorldStatisticsCallback {


    fun setup(lifecycle: Lifecycle) {
        autoDisposable.bindTo(lifecycle)
    }

    private val _data = MutableLiveData<List<TimelineDataItem>>()
    val data: LiveData<List<TimelineDataItem>> = _data
    override fun onSuccess(data: List<TimelineDataItem>) {
        _data.postValue(data)
    }

    fun getData(date: String? = null) {
        getDistinctDailyWorldStatisticsUseCase.execute(getDate(), this, autoDisposable)
    }

    private fun getDate(): String {
        val dateFormat = SimpleDateFormat("MM/dd/yy", Locale.getDefault());
        val date = Date();
        val stringDate = dateFormat.format(date);
        return when {
            stringDate.split("/")[0].toInt() < 10 -> {
                stringDate.substring(1)
            }
            else -> stringDate
        }
    }
}
