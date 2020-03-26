package com.fasoh.corona.models.country

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.fasoh.corona.models.BaseStatisticItem
import com.fasoh.corona.models.global.Source
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull
import java.util.*

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
