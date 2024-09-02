package com.phung.catastrophicapp.di

import com.phung.catastrophicapp.BuildConfig
import com.phung.catastrophicapp.data.network.ApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    // retrofit
    single {
        val headersInterceptor = Interceptor { chain ->
            val original: Request = chain.request()
            val newBuilder = original.newBuilder()
                .addHeader("x-api-key", BuildConfig.CAT_API_KEY)
            val request = newBuilder.method(original.method, original.body).build()
            val response = chain.proceed(request)
            response
        }

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addNetworkInterceptor(headersInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // cat api service
    single { get<Retrofit>().create(ApiService::class.java) }
}