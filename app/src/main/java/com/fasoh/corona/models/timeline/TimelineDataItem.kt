package com.fasoh.corona.models.timeline

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

//Quick hacks without normalization
@Entity(tableName = "time_line_data_items")
data class TimelineDataItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    var date: String? = null,

    var longDate: Long? = 0,

    var month: Int? = 0,

    @SerializedName("countrylabel")
    val countryLabel: String? = null,

    @field:SerializedName("countrycode")
    val countryCode: String? = null,

    @SerializedName("totaldeaths")
    var totalDeaths: String = "0",

    @SerializedName("totalcases")
    val totalCases: String = "0",

    @SerializedName("totalrecovered")
    val totalRecovered: String = "0"
){
    override fun toString(): String {
        return "TimelineDataItem(id=$id, date=$date, longDate=$longDate, countryLabel=$countryLabel, countryCode=$countryCode, totalDeaths=$totalDeaths, totalCases=$totalCases, totalRecovered=$totalRecovered)"
    }
}