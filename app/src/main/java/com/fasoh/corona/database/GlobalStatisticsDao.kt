package com.fasoh.corona.database

import androidx.room.Dao
import androidx.room.Query
import com.fasoh.corona.models.global.GlobalStatisticsItem
import com.ungagroup.mwananchi.db.BaseDao
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface GlobalStatisticsDao: BaseDao<GlobalStatisticsItem> {

    @Query("select * from global_statistics_items limit 1")
    fun findStatistics(): Flowable<GlobalStatisticsItem?>

    @Query("select * from global_statistics_items limit 1")
    fun findStatisticsi(): Maybe<GlobalStatisticsItem>

    @Query("select count(*) from global_statistics_items")
    fun count(): Flowable<Int>

}