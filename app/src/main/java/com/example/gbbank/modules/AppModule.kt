package com.example.gbbank.modules

import com.example.gbbank.data.CryptoApi
import com.example.gbbank.data.CryptoInterceptor
import com.example.gbbank.data.RatesApi
import com.example.gbbank.data.RatesInterceptor
import com.example.gbbank.repositories.*
import com.example.gbbank.utils.ResponseHandler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRegisterRepository(
        auth: FirebaseAuth, handler: ResponseHandler, repository: DbAddUserRepository
    )
            : RegisterRepository = RegisterRepository(auth, handler, repository)

    @Provides
    @Singleton
    fun provideLoginRepository(
        auth: FirebaseAuth, handler: ResponseHandler
    ): LoginRepository = LoginRepository(auth, handler)

    @Provides
    @Singleton
    fun provideResetPasswordRepository(
        auth: FirebaseAuth, handler: ResponseHandler
    ): ResetPasswordRepository = ResetPasswordRepository(auth, handler)

    @Provides
    @Singleton
    fun provideAddUserDbRepository(
        handler: ResponseHandler, db: FirebaseDatabase
    ): DbAddUserRepository = DbAddUserRepository(handler, db)

    @Provides
    @Singleton
    fun provideAddBalanceRepository(
        auth: FirebaseAuth, db: FirebaseDatabase, handler: ResponseHandler
    ): DbAddBalanceRepository = DbAddBalanceRepository(auth, db, handler)

    @Provides
    @Singleton
    fun provideResponseHandler() = ResponseHandler()

    @Provides
    @Singleton
    fun provideCryptoRepository(
        api: CryptoApi,
        handler: ResponseHandler
    ):CryptoRepository = CryptoRepository(api, handler)

    @Provides
    @Singleton
    fun provideCryptoApi(): CryptoApi = Retrofit.Builder()
        .baseUrl("https://api.coingecko.com")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().apply { addInterceptor(CryptoInterceptor()) }.build())
        .build()
        .create(CryptoApi::class.java)

    @Provides
    @Singleton
    fun provideExchangeRepository(
        api: RatesApi,
        handler: ResponseHandler
    ):RatesRepository = RatesRepository(api, handler)

    @Provides
    @Singleton
    fun provideExchangeApi(): RatesApi = Retrofit.Builder()
        .baseUrl("https://test-api.tbcbank.ge")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().apply { addInterceptor(RatesInterceptor()) }.build())
        .build()
        .create(RatesApi::class.java)
}