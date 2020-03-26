package com.ungagroup.mwananchi.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fasoh.corona.database.CountryStatisticItemDao
import com.fasoh.corona.database.GlobalStatisticsDao
import com.fasoh.corona.database.TimelineItemDao
import com.fasoh.corona.models.country.CountryDataItem
import com.fasoh.corona.models.global.GlobalStatisticsItem
import com.fasoh.corona.models.timeline.TimelineDataItem

@Database(
    entities = [
        GlobalStatisticsItem::class,
        CountryDataItem::class,
        TimelineDataItem::class
    ],
    version = 1
)
abstract class CoronaDatabase : RoomDatabase() {

    abstract fun globalStatisticsDao(): GlobalStatisticsDao

    abstract fun countryStatisticsDao(): CountryStatisticItemDao

    abstract fun timeLineItemsDao(): TimelineItemDao
}