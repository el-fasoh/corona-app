package com.fasoh.corona.repositories

import com.fasoh.corona.database.TimelineItemDao
import com.fasoh.corona.extentions.dateToMilliSeconds
import com.fasoh.corona.models.StatisticsDto
import com.fasoh.corona.models.timeline.Timeline
import com.fasoh.corona.models.timeline.TimelineDataItem
import com.fasoh.corona.network.TheVirusTrackerApi
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import kotlin.collections.ArrayList

interface StatisticsRepository {
    fun getTimeLineDataByCode(month: Int, code: String): Single<StatisticsDto>

    fun getTimeLineDataByDate(date: String): Single<List<TimelineDataItem>>
}

class StatisticsRepositoryImpl(
    private val api: TheVirusTrackerApi,
    private val timelineItemDao: TimelineItemDao
) : StatisticsRepository {
    override fun getTimeLineDataByCode(month: Int, code: String): Single<StatisticsDto> {
        return timelineItemDao.count()
            .flatMap {
                if (it == 0) {
                    api.getTimelineData()
                        .flatMap { timeline ->
                            saveTimelineData(formatTimelines(timeline))
                                .andThen(timelineItemDao.getTimelineData(month,code))
                        }.subscribeOn(Schedulers.io())

                } else {
                    timelineItemDao.getTimelineData(month,code)
                }
            }.doOnError {
                Timber.e(it)
            }.subscribeOn(Schedulers.io())
    }

    override fun getTimeLineDataByDate(date: String): Single<List<TimelineDataItem>> {
        return  timelineItemDao.count()
            .flatMap {
                if (it == 0) {
                    api.getTimelineData()
                        .flatMap { timeline ->
                            saveTimelineData(formatTimelines(timeline))
                                .andThen(timelineItemDao.selectDailyStatistics(date))
                        }
                        .doOnError {
                            Timber.e(it)
                        }
                }else{
                    timelineItemDao.selectDailyStatistics(date)
                }
            }.doOnError {
                Timber.e(it)
            }
    }

    private fun formatTimelines(timeline: Timeline): List<TimelineDataItem> {
        timeline.data.forEach { timelineDataItem ->
            timelineDataItem.month = timelineDataItem.date!!.split("/")[0].toInt()
            timelineDataItem.longDate = timelineDataItem.date?.dateToMilliSeconds()

        }
        return timeline.data
    }

    private fun saveTimelineData(timelines: List<TimelineDataItem>): Completable {
        return Completable.fromAction {
                Timber.e("Saving timelines")
                timelineItemDao.deleteAll()
                timelineItemDao.insert(timelines)
                //Timber.e(timelineItemDao.findAggregateCountryTimelineDataByCode("US").toString())
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

}