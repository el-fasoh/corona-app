package com.fasoh.corona.usecases

import com.fasoh.corona.AutoDisposable
import com.fasoh.corona.extentions.addTo
import com.fasoh.corona.models.StatisticsDto
import com.fasoh.corona.repositories.StatisticsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

interface GetTimelineDataUseCase {
    fun execute(
        month: Int,
        code: String,
        autoDisposable: AutoDisposable,
        callback: GetTimelineDataUseCaseCallback
    )
}

class GetTimelineDataUseCaseImp (private val statisticsRepository: StatisticsRepository): GetTimelineDataUseCase{
    override fun execute(
        month: Int,
        code: String,
        autoDisposable: AutoDisposable,
        callback: GetTimelineDataUseCaseCallback
    ) {
       statisticsRepository.getTimeLineDataByCode(month, code)
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe({
               callback.onSuccess(it)
           },{
               callback.onSuccess(StatisticsDto())
           }).addTo(autoDisposable)
    }
}

interface GetTimelineDataUseCaseCallback{
    fun onSuccess(timelineData: StatisticsDto)
}