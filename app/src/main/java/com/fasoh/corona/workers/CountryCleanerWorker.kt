package com.fasoh.corona.workers

import android.content.Context
import androidx.work.*
import com.fasoh.corona.BuildConfig
import com.fasoh.corona.database.CountryStatisticItemDao
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber
import java.lang.Exception
import java.util.*
import java.util.concurrent.TimeUnit

class CountryCleanerWorker (context: Context,
                            params: WorkerParameters
) : Worker(context, params), KoinComponent {

    private val countryStatisticItemDao: CountryStatisticItemDao by inject()
    override fun doWork(): Result {
        Timber.i("Running cleaner")
        return try {
            val subTime: Long = if(BuildConfig.DEBUG){
                30000
            }else{
                1200000
            }
            val dateBefore = Calendar.getInstance().timeInMillis - subTime
            val deleted = countryStatisticItemDao.deleteStaleData(dateBefore)
            Timber.d("Delete entries: $deleted")

            Result.success()
        }catch (ex: Exception){
            Result.failure()
        }
    }

    companion object{
        fun schedule(context: Context){
            val constraints = Constraints.Builder().apply {
                setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                setRequiresCharging(false)
                setRequiresStorageNotLow(false)
            }.build()

            val request: PeriodicWorkRequest = when{
                BuildConfig.DEBUG -> PeriodicWorkRequestBuilder<CountryCleanerWorker>(
                    30,
                    TimeUnit.MINUTES
                ).setConstraints(constraints)
                    .build()
                else -> PeriodicWorkRequestBuilder<CountryCleanerWorker>(
                    20,
                    TimeUnit.MINUTES
                ).setConstraints(constraints)
                    .build()
            }

            WorkManager.getInstance(context).enqueue(request)
        }
    }

}