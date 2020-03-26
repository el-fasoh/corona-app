package com.fasoh.corona.usecases

import com.fasoh.corona.AutoDisposable
import com.fasoh.corona.extentions.addTo
import com.fasoh.corona.models.country.CountryDataItem
import com.fasoh.corona.repositories.CountryStatisticsRepository
import timber.log.Timber

interface GetCountryStatisticsUseCase {
    fun execute(code: String, callback: GetCountryStatisticsUseCaseCallback, autoDisposable: AutoDisposable)
}

class GetCountryStatisticsUseCaseImpl(private val countryStatisticsRepository: CountryStatisticsRepository) :
    GetCountryStatisticsUseCase {
    override fun execute(
        code: String,
        callback: GetCountryStatisticsUseCaseCallback,
        autoDisposable: AutoDisposable
    ) {
        countryStatisticsRepository.getCountryStatistic(code)
            .subscribe({
                callback.onSuccess(it)
            },{
                Timber.e(it)
                callback.onSuccess(CountryDataItem(code,0L))
            }).addTo(autoDisposable)

    }

}

interface GetCountryStatisticsUseCaseCallback {
    fun onSuccess(countryDataItem: CountryDataItem)
}