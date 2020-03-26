package com.fasoh.corona.models

import androidx.room.Ignore
import com.google.gson.annotations.SerializedName

open class BaseStatisticItem (
    @SerializedName("total_active_cases")
    var totalActiveCases: Int? = 0,

    @SerializedName("total_new_deaths_today")
    var totalNewDeathsToday: Int? = 0,

    @SerializedName("total_cases")
    var totalCases: Int? = 0,

    @SerializedName("total_deaths")
    var totalDeaths: Int? = 0,

    @SerializedName("total_serious_cases")
    var totalSeriousCases: Int? = 0,

    @SerializedName("total_recovered")
    var totalRecovered: Int? = 0,

    @SerializedName("total_unresolved")
    var totalUnresolved: Int? = 0,

    @SerializedName("total_new_cases_today")
    var totalNewCasesToday: Int? = 0
)