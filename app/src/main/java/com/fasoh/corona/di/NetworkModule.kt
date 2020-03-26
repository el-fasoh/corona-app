package com.fasoh.corona.di

import android.content.Context
import com.fasoh.corona.BuildConfig
import com.fasoh.corona.network.TheVirusTrackerApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit


val networkModule = module {
    fun provideGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    fun provideHttpClient(logger: HttpLoggingInterceptor, chuckInterceptor: ChuckInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(logger)
            .addInterceptor(chuckInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()
    }

    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.HOST)
                .addConverterFactory(GsonConverterFactory.create(factory))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
    }

    fun provideLogger(): HttpLoggingInterceptor {
       val logger = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.tag("OkHttp:").d(message)
            }
        })
        logger.level = HttpLoggingInterceptor.Level.HEADERS
        return logger
    }

   

    fun provideUngaApi(retrofit: Retrofit): TheVirusTrackerApi {
        return retrofit.create(TheVirusTrackerApi::class.java)
    }

    fun provideChuckInterceptor(context: Context):ChuckInterceptor{
        return ChuckInterceptor(context)
    }

    single { provideGson() }
    single { provideHttpClient(get(),get()) }
    single { provideRetrofit(get(), get()) }
    single { provideLogger() }
    single { provideUngaApi(get()) }
    single { provideChuckInterceptor(get()) }
}