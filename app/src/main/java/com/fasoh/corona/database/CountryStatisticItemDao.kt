package com.fasoh.corona.database

import androidx.room.Dao
import androidx.room.Query
import com.fasoh.corona.models.country.CountryDataItem
import com.ungagroup.mwananchi.db.BaseDao
import io.reactivex.Flowable

@Dao
interface CountryStatisticItemDao : BaseDao<CountryDataItem> {

    @Query("select * from country_statistics where id = :code")
    fun getCountryStatisticByCode(code: String): Flowable<CountryDataItem>

    @Query("select count(*) from country_statistics where id = :code")
    fun countByCode(code: String): Flowable<Int>

    @Query("delete from country_statistics where lastFetched<:date")
    fun deleteStaleData(date: Long): Int

    @Query("select * from country_statistics")
    fun getAll(): List<CountryDataItem>
}