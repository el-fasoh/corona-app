package com.fasoh.corona.di

import com.fasoh.corona.repositories.CountryStatisticsRepository
import com.fasoh.corona.repositories.GlobalStatisticsRepository
import com.fasoh.corona.repositories.StatisticsRepository
import com.fasoh.corona.usecases.*
import org.koin.dsl.module

val useCaseModule = module {

    fun provideGetGlobalStatisticsUseCaseModule(repository: GlobalStatisticsRepository): GetGlobalStatisticsUseCase {
        return GetGlobalStatisticsUseCaseImp(repository)
    }

    single { provideGetGlobalStatisticsUseCaseModule(get()) }


    fun provideGetCountryStatisticsUseCase(countryStatisticsRepository: CountryStatisticsRepository): GetCountryStatisticsUseCase {
        return GetCountryStatisticsUseCaseImpl(countryStatisticsRepository)
    }

    single { provideGetCountryStatisticsUseCase(get()) }

    fun provideGetTimelineDataUseCase(statisticsRepository: StatisticsRepository): GetTimelineDataUseCase {
        return GetTimelineDataUseCaseImp(statisticsRepository)
    }

    single { provideGetTimelineDataUseCase(get()) }

    fun provideGetDistinctDailyWorldStatisticsImpl(statisticsRepository: StatisticsRepository): GetDistinctDailyWorldStatisticsUseCase {
        return GetDistinctDailyWorldStatisticsUseCaseImpl(statisticsRepository)
    }
    single {
        provideGetDistinctDailyWorldStatisticsImpl(get())
    }
}