package com.fasoh.corona.network

import com.fasoh.corona.models.country.CountryData
import com.fasoh.corona.models.global.GlobalStatistics
import com.fasoh.corona.models.timeline.Timeline
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TheVirusTrackerApi {

    @GET("free-api")
    fun getCountryStatistics(@Query("countryTotal") countryCode: String): Flowable<CountryData>

    @GET("free-api")
    fun getGlobalStatistics(@Query("global") global: String? = "stats"):Flowable<GlobalStatistics>

    @GET("timeline/map-data.json")
    fun getTimelineData():Flowable<List<Timeline>>
}