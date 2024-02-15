package com.example.currencyexchanger.data.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal val retrofitModule = module {
    single { provideLoggerInterceptor() }
    single { provideOkHttpClient(get()) }
    single<Converter.Factory> { provideConverterFactory() }
    single { provideRetrofit(get(), get()) }
}

private fun provideLoggerInterceptor() = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private const val READ_TIME_OUT = 10L
private const val CONNECT_TIME_OUT = 10L

private fun provideOkHttpClient(interceptor: HttpLoggingInterceptor) = OkHttpClient.Builder()
    .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
    .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
    .addInterceptor(interceptor).build()

private fun provideConverterFactory() = GsonConverterFactory.create()

private fun provideRetrofit(client: OkHttpClient, converterFactory: Converter.Factory) =
    Retrofit.Builder()
        .baseUrl("https://developers.paysera.com")
        .client(client)
        .addConverterFactory(converterFactory)
        .build()
