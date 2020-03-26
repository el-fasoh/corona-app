package com.fasoh.corona.repositories

import com.fasoh.corona.database.TimelineItemDao
import com.fasoh.corona.extentions.dateToMilliSeconds
import com.fasoh.corona.models.StatisticsDto
import com.fasoh.corona.models.timeline.Timeline
import com.fasoh.corona.models.timeline.TimelineDataItem
import com.fasoh.corona.network.TheVirusTrackerApi
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import kotlin.collections.ArrayList

interface StatisticsRepository {
    fun getTimeLineDataByCode(month: Int, code: String): Flowable<StatisticsDto>
}

class StatisticsRepositoryImpl(
    private val api: TheVirusTrackerApi,
    private val timelineItemDao: TimelineItemDao
) : StatisticsRepository {
    override fun getTimeLineDataByCode(month: Int, code: String): Flowable<StatisticsDto> {
        return timelineItemDao.count()
            .take(1)
            .flatMap {
                if (it == 0) {
                    api.getTimelineData()
                        .take(1)
                        .flatMap { timelines ->
                            saveTimelineData(formatTimelines(timelines))
                                .andThen(timelineItemDao.getTimelineData(month,code))
                        }

                } else {
                    timelineItemDao.getTimelineData(month,code)
                }
            }
    }

    private fun formatTimelines(timelines: List<Timeline>): ArrayList<TimelineDataItem> {
        val list = ArrayList<TimelineDataItem>()
        timelines.forEach { timeline ->
            timeline.data.forEach { timelineDataItem ->
                timelineDataItem.date = timeline.date
                timelineDataItem.month = timelineDataItem.date!!.split("/")[0].toInt()
                timelineDataItem.longDate = timeline.date?.dateToMilliSeconds()
                list.add(timelineDataItem)
            }

        }
        return list
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