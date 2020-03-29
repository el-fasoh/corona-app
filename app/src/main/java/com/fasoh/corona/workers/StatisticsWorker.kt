package com.fasoh.corona.workers

import android.content.Context
import androidx.work.*
import com.fasoh.corona.BuildConfig
import com.fasoh.corona.database.TimelineItemDao
import com.fasoh.corona.repositories.StatisticsRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber
import java.util.concurrent.TimeUnit

class StatisticsWorker (context: Context,
                        params: WorkerParameters
) : Worker(context, params), KoinComponent {

    private val statisticsRepo: StatisticsRepository by inject()
    private val timelineItemDao: TimelineItemDao by inject()
    override fun doWork(): Result {
        return try {
            Timber.e("Statistics worker starting pruning: ${timelineItemDao.deleteAll()} entries")
            val v= statisticsRepo.getTimeLineDataByCode(1, "US").blockingGet()
            when {
                v.items.isEmpty() -> {
                    Result.failure()
                }
                else -> Result.success()
            }
        }catch (ex: Exception){
            Timber.e(ex)
            Result.failure()
        }
    }

    companion object{
        fun schedule(context: Context){
            val constraints = Constraints.Builder().apply {
                setRequiredNetworkType(NetworkType.CONNECTED)
                setRequiresCharging(false)
                setRequiresStorageNotLow(false)
            }.build()

            val request: PeriodicWorkRequest = when{
                BuildConfig.DEBUG -> PeriodicWorkRequestBuilder<StatisticsWorker>(
                    30,
                    TimeUnit.MINUTES
                ).setConstraints(constraints)
                    .build()
                else -> PeriodicWorkRequestBuilder<StatisticsWorker>(
                    4,
                    TimeUnit.HOURS
                ).setConstraints(constraints)
                    .build()
            }

            WorkManager.getInstance(context).enqueue(request)
        }
    }


}