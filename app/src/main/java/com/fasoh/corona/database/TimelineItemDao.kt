package com.fasoh.corona.database

import androidx.room.Dao
import androidx.room.Query
import com.fasoh.corona.models.StatisticsDto
import com.fasoh.corona.models.timeline.TimelineDataItem
import com.ungagroup.mwananchi.db.BaseDao
import io.reactivex.Flowable

@Dao
interface TimelineItemDao : BaseDao<TimelineDataItem> {

    @Query("select count(*) from time_line_data_items")
    fun count(): Flowable<Int>

    @Query("select * from time_line_data_items where countryCode = :code and month = :month order by longDate asc")
    fun findCountryTimelineDataByCode(month: Int, code: String): Flowable<List<TimelineDataItem>>

    @Query(
        """select 
        max(totalCases) as maximum, 
        min(totalCases) as minimum
        from time_line_data_items where countryCode=:code and month = :month"""
    )
    fun findMaxAndMinForAMonth(month: Int, code: String): StatisticsDto

    fun getTimelineData(month: Int, code: String): Flowable<StatisticsDto> {
        return findCountryTimelineDataByCode(month, code)
            .flatMap { found->
                val stats = findMaxAndMinForAMonth(month,code)
                stats.apply {
                    items =found
                }
                Flowable.just(stats)
            }
    }


    @Query("delete from time_line_data_items")
    fun deleteAll(): Int
}