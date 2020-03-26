package com.fasoh.corona.models

import androidx.room.ColumnInfo
import androidx.room.Ignore
import com.fasoh.corona.models.timeline.TimelineDataItem


data class StatisticsDto(
    @ColumnInfo(name = "maximum") var maximum: Int = 0,
    @ColumnInfo(name = "minimum") var minimum: Int = 0,
    @Ignore var items: List<TimelineDataItem> = emptyList()
){
    override fun toString(): String {
        return "StatisticsDto2(maximum=$maximum, minimum=$minimum)"
    }
}