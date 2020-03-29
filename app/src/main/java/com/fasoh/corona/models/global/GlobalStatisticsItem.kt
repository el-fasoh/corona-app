package com.fasoh.corona.models.global

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasoh.corona.models.BaseStatisticItem

@Entity(tableName = "global_statistics_items")
data class GlobalStatisticsItem(
	@PrimaryKey
	var id: Long = 1

): BaseStatisticItem()
