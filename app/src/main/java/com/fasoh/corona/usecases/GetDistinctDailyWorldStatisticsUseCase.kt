package com.fasoh.corona.usecases

import com.fasoh.corona.AutoDisposable
import com.fasoh.corona.extentions.addTo
import com.fasoh.corona.models.timeline.TimelineDataItem
import com.fasoh.corona.repositories.StatisticsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

interface GetDistinctDailyWorldStatisticsUseCase {
    fun execute(date: String,callback: GetDistinctDailyWorldStatisticsCallback, autoDisposable: AutoDisposable)
}

class GetDistinctDailyWorldStatisticsUseCaseImpl(private val statisticsRepository: StatisticsRepository) :
    GetDistinctDailyWorldStatisticsUseCase {
    override fun execute(
        date: String,
        callback: GetDistinctDailyWorldStatisticsCallback,
        autoDisposable: AutoDisposable
    ) {
       statisticsRepository.getTimeLineDataByDate(date)
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe({
               callback.onSuccess(it)
           },{
               Timber.e(it)
           }) .addTo(autoDisposable)
    }

}

interface GetDistinctDailyWorldStatisticsCallback {
    fun onSuccess(data: List<TimelineDataItem>)
}