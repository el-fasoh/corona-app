package com.fasoh.corona.di

import com.fasoh.corona.database.CountryStatisticItemDao
import com.fasoh.corona.database.GlobalStatisticsDao
import com.fasoh.corona.database.TimelineItemDao
import com.fasoh.corona.network.TheVirusTrackerApi
import com.fasoh.corona.repositories.*
import org.koin.dsl.module

val repositoryModule = module {

    fun provideGlobalStatisticsRepository(
        globalStatisticsDao: GlobalStatisticsDao,
        api: TheVirusTrackerApi
    ): GlobalStatisticsRepository {
        return GlobalStatisticsRepositoryImpl(globalStatisticsDao, api)
    }


    fun provideCountryStatisticsRepository(
        countryStatisticItemDao: CountryStatisticItemDao,
        api: TheVirusTrackerApi
    ): CountryStatisticsRepository {
        return CountryStatisticsRepositoryImpl(countryStatisticItemDao, api)
    }

    fun provideStatisticsRepository(timelineItemDao: TimelineItemDao,api: TheVirusTrackerApi): StatisticsRepository{
        return StatisticsRepositoryImpl(api,timelineItemDao)
    }

    single {
        SettingsRepository.getInstance(get())
    }

    single { provideCountryStatisticsRepository(get(),get()) }
    single { provideStatisticsRepository(get(), get()) }
    single { provideGlobalStatisticsRepository(get(), get()) }
}