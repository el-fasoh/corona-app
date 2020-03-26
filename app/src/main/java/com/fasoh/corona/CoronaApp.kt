package com.fasoh.corona

import android.app.Application
import androidx.multidex.MultiDex
import com.fasoh.corona.di.databaseModule
import com.fasoh.corona.di.networkModule
import com.fasoh.corona.di.repositoryModule
import com.fasoh.corona.di.useCaseModule
import com.fasoh.corona.workers.CountryCleanerWorker
import com.fasoh.corona.workers.GlobalStatisticsWorker
import com.fasoh.corona.workers.StatisticsWorker
import com.ungagroup.mwananchi.di.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class CoronaApp : Application(){

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@CoronaApp)
            modules(listOf(networkModule, viewModelModule, repositoryModule,
                useCaseModule, databaseModule))
        }

        GlobalStatisticsWorker.schedule(this)

        CountryCleanerWorker.schedule(this)

        StatisticsWorker.schedule(this)
    }
}