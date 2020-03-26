package com.fasoh.corona.di

import android.content.Context
import androidx.room.Room
import com.fasoh.corona.database.CountryStatisticItemDao
import com.ungagroup.mwananchi.db.CoronaDatabase
import com.fasoh.corona.database.GlobalStatisticsDao
import com.fasoh.corona.database.TimelineItemDao
import org.koin.dsl.module

val databaseModule = module {

    fun providerRoom(context: Context): CoronaDatabase{
        return Room.databaseBuilder(context, CoronaDatabase::class.java, "corona-db")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideGlobalStatisticsDao(db: CoronaDatabase): GlobalStatisticsDao {
        return db.globalStatisticsDao()
    }

    fun provideCountryStatisticsDao(db: CoronaDatabase): CountryStatisticItemDao{
        return db.countryStatisticsDao()
    }

    fun provideTimelineItemDao(db: CoronaDatabase): TimelineItemDao{
        return db.timeLineItemsDao()
    }


    single { provideCountryStatisticsDao(get()) }

    single { providerRoom(get()) }

    single { provideGlobalStatisticsDao(get()) }

    single { provideTimelineItemDao(get()) }
}