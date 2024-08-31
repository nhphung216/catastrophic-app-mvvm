package com.phung.catastrophicapp.di

import androidx.room.Room
import com.phung.catastrophicapp.data.local.CatImageDatabase
import com.phung.catastrophicapp.data.network.ApiService
import com.phung.catastrophicapp.data.repository.CatRepositoryImpl
import com.phung.catastrophicapp.domain.repository.CatRepository
import com.phung.catastrophicapp.utils.EVNConfigs
import com.phung.catastrophicapp.viewmodel.CatViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    // retrofit
    single {
        val headersInterceptor = Interceptor { chain ->
            val original: Request = chain.request()
            val newBuilder = original.newBuilder()
                .addHeader("x-api-key", EVNConfigs.API_KEY)
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
            .baseUrl(EVNConfigs.SERVER_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // cat api service
    single { get<Retrofit>().create(ApiService::class.java) }

    // room database
    single {
        Room.databaseBuilder(
            androidContext(),
            CatImageDatabase::class.java,
            "cat_image_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    // DAO
    single { get<CatImageDatabase>().catImageDao() }

    // repository
    single<CatRepository> { CatRepositoryImpl(get(), get(), get()) }

    // viewModel
    viewModel { CatViewModel(get()) }
}