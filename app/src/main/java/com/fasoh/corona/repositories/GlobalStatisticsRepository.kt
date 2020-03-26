package com.fasoh.corona.repositories

import com.fasoh.corona.models.global.GlobalStatisticsItem
import com.fasoh.corona.network.TheVirusTrackerApi
import com.fasoh.corona.database.GlobalStatisticsDao
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*

interface GlobalStatisticsRepository {

    fun saveGlobalStatisticsItem(items: List<GlobalStatisticsItem>): Completable

    fun getGlobalStatistics(): Flowable<GlobalStatisticsItem>
}

class GlobalStatisticsRepositoryImpl constructor(
    private val globalStatisticsDao: GlobalStatisticsDao,
    private val api: TheVirusTrackerApi
) : GlobalStatisticsRepository {

    override fun saveGlobalStatisticsItem(items: List<GlobalStatisticsItem>): Completable {
        return Completable.fromAction {
                globalStatisticsDao.insert(items)
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getGlobalStatistics(): Flowable<GlobalStatisticsItem> {
        return globalStatisticsDao.count()
            .flatMap { count ->
                if (count == 0) {
                    api.getGlobalStatistics()
                        .take(1)
                        .flatMap {
                            saveGlobalStatisticsItem(it.results)
                                .andThen(globalStatisticsDao.findStatistics())

                        }
                } else {
                    globalStatisticsDao.findStatistics()
                }
            }
    }


}