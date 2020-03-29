package com.fasoh.corona.database

import androidx.room.Dao
import androidx.room.Query
import com.fasoh.corona.models.StatisticsDto
import com.fasoh.corona.models.timeline.TimelineDataItem
import com.ungagroup.mwananchi.db.BaseDao
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

@Dao
interface TimelineItemDao : BaseDao<TimelineDataItem> {

    @Query("select count(*) from time_line_data_items")
    fun count(): Single<Int>

    @Query("select * from time_line_data_items where countryCode = :code and month = :month order by longDate asc")
    fun findCountryTimelineDataByCode(month: Int, code: String): Single<List<TimelineDataItem>>

    @Query(
        """select 
        max(totalCases) as maximum, 
        min(totalCases) as minimum
        from time_line_data_items where countryCode=:code and month = :month"""
    )
    fun findMaxAndMinForAMonth(month: Int, code: String): StatisticsDto

    fun getTimelineData(month: Int, code: String): Single<StatisticsDto> {
        return findCountryTimelineDataByCode(month, code)
            .flatMap { found->
                val stats = findMaxAndMinForAMonth(month,code)
                stats.apply {
                    items =found
                }
                Single.just(stats)
            }.subscribeOn(Schedulers.io())
    }


    @Query("delete from time_line_data_items")
    fun deleteAll(): Int

    @Query("select distinct id, countryCode, totalCases, totalRecovered ,totalDeaths from time_line_data_items where date = :date")
    fun selectDailyStatistics(date: String):Single<List<TimelineDataItem>>
}