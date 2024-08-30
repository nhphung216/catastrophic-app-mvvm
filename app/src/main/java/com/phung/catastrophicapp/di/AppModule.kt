package com.phung.catastrophicapp.di

import android.text.TextUtils
import com.phung.catastrophicapp.data.remote.ApiService
import com.phung.catastrophicapp.data.repository.CatRepositoryImpl
import com.phung.catastrophicapp.domain.repository.CatRepository
import com.phung.catastrophicapp.utils.EVNConfigs
import com.phung.catastrophicapp.viewmodel.CatViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    // retrofit
    single {
//        val headersInterceptor = Interceptor { chain ->
//            val original: Request = chain.request()
//            val newBuilder = original.newBuilder()
//                .addHeader("x-api-key", EVNConfigs.API_KEY)
//            val request = newBuilder.method(original.method, original.body).build()
//            val response = chain.proceed(request)
//            response
//        }

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
//            .addNetworkInterceptor(headersInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl(EVNConfigs.SERVER_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // cat api service
    single { get<Retrofit>().create(ApiService::class.java) }

    // repository
    single<CatRepository> { CatRepositoryImpl(get()) }

    // viewModel
    viewModel { CatViewModel(get()) }
}