package com.fasoh.corona.repositories

import com.fasoh.corona.database.CountryStatisticItemDao
import com.fasoh.corona.models.country.CountryDataItem
import com.fasoh.corona.network.TheVirusTrackerApi
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

interface CountryStatisticsRepository {
    fun getCountryStatistic(code: String): Flowable<CountryDataItem>
}

class CountryStatisticsRepositoryImpl(
    private val countryStatisticItemDao: CountryStatisticItemDao,
    private val api: TheVirusTrackerApi
) : CountryStatisticsRepository {
    override fun getCountryStatistic(code: String): Flowable<CountryDataItem> {
        return countryStatisticItemDao.countByCode(code)
            .flatMap {
                if(it == 0){
                    api.getCountryStatistics(code)
                        .take(1)
                        .flatMap {data->
                            val dataItem =  data.countrydata[0]
                            dataItem.id = code
                            dataItem.lastFetched = Calendar.getInstance().timeInMillis
                            saveCountryItem(dataItem)
                                .andThen(countryStatisticItemDao.getCountryStatisticByCode(code))
                        }
                }else{
                    countryStatisticItemDao.getCountryStatisticByCode(code)
                }
            }
    }

    private fun saveCountryItem(countryDataItem: CountryDataItem): Completable{
        return Completable.fromAction {
            countryStatisticItemDao.insert(countryDataItem)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}