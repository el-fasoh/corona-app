package com.fasoh.corona.models.country

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasoh.corona.models.BaseStatisticItem
import org.jetbrains.annotations.NotNull

@Entity(tableName = "country_statistics")
data class CountryDataItem(
	@PrimaryKey
	@NotNull
	var id: String,

	var lastFetched: Long
): BaseStatisticItem(){
	override fun toString(): String {
		return "CountryDataItem(id='$id', lastFetched=$lastFetched)"
	}
}
