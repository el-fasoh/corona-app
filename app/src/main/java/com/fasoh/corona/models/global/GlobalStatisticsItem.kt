package com.fasoh.corona.models.global

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.fasoh.corona.models.BaseStatisticItem
import com.google.gson.annotations.SerializedName

@Entity(tableName = "global_statistics_items")
data class GlobalStatisticsItem(
	@PrimaryKey
	var id: Long = 1

): BaseStatisticItem()
