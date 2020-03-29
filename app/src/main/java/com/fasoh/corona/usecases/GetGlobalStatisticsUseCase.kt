package com.fasoh.corona.usecases

import com.fasoh.corona.AutoDisposable
import com.fasoh.corona.extentions.addTo
import com.fasoh.corona.models.global.GlobalStatisticsItem
import com.fasoh.corona.repositories.GlobalStatisticsRepository

interface GetGlobalStatisticsUseCase {
    fun execute(callback: GetGlobalStatisticsUseCaseCallback, autoDisposable: AutoDisposable)
}

class GetGlobalStatisticsUseCaseImp constructor(private val globalStatisticsRepository: GlobalStatisticsRepository) :
    GetGlobalStatisticsUseCase {
    override fun execute(
        callback: GetGlobalStatisticsUseCaseCallback,
        autoDisposable: AutoDisposable
    ) {

        globalStatisticsRepository.getGlobalStatistics()
            .subscribe({item->
                callback.onSuccess(item)

            },{
                callback.onSuccess(GlobalStatisticsItem(1L))
            }).addTo(autoDisposable)
    }


   }

interface GetGlobalStatisticsUseCaseCallback {
    fun onSuccess(globalStatistics: GlobalStatisticsItem)
}