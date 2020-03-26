package com.fasoh.corona.ui.dashboard

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fasoh.corona.models.StatisticsDto
import com.fasoh.corona.ui.BaseViewModel
import com.fasoh.corona.usecases.GetTimelineDataUseCase
import com.fasoh.corona.usecases.GetTimelineDataUseCaseCallback
import java.util.*

class DashboardViewModel(private val getTimelineDataUseCase: GetTimelineDataUseCase) :
    BaseViewModel() {

    fun setup(lifecycle: Lifecycle) {
        autoDisposable.bindTo(lifecycle)
        getCountryData()
    }

    private val _timeline = MutableLiveData<StatisticsDto>()
    val timeline: LiveData<StatisticsDto> = _timeline
    private val callback = object : GetTimelineDataUseCaseCallback {
        override fun onSuccess(timelineData: StatisticsDto) {
            _timeline.postValue(timelineData)
        }
    }

    fun getCountryData(
        month: Int? = Calendar.getInstance().get(Calendar.MONTH) + 1,
        code: String? = Locale.getDefault().country
    ) {
        getTimelineDataUseCase.execute(month!!, code!!, autoDisposable, callback)
    }

}