package com.fasoh.corona.workers

import android.content.Context
import androidx.work.*
import com.fasoh.corona.BuildConfig
import com.fasoh.corona.database.GlobalStatisticsDao
import com.fasoh.corona.network.TheVirusTrackerApi
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber
import java.util.concurrent.TimeUnit

class GlobalStatisticsWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params), KoinComponent {

    private val api: TheVirusTrackerApi by inject()
    private val globalStatisticsDao: GlobalStatisticsDao by inject()
    override fun doWork(): Result {
        Timber.d("Refreshing global statistics")
        return try {
            globalStatisticsDao.insert(
                api.getGlobalStatistics()
                    .take(1)
                    .blockingFirst()
                    .results
            )
            Result.success()
        } catch (ex: Exception) {
            Timber.e(ex)
            Result.failure()
        }
    }

    companion object{
        fun schedule(context: Context){
            val constraints = Constraints.Builder().apply {
                setRequiredNetworkType(NetworkType.CONNECTED)
                setRequiresCharging(false)
                setRequiresStorageNotLow(true)
            }.build()

            val request: PeriodicWorkRequest = when{
                BuildConfig.DEBUG -> PeriodicWorkRequestBuilder<GlobalStatisticsWorker>(
                    30,
                    TimeUnit.MINUTES
                ).setConstraints(constraints)
                    .build()
                else -> PeriodicWorkRequestBuilder<GlobalStatisticsWorker>(
                    20,
                    TimeUnit.MINUTES
                ).setConstraints(constraints)
                    .build()
            }

            WorkManager.getInstance(context).enqueue(request)
        }
    }

}