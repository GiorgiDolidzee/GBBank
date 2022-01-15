package com.example.gbbank.di

import com.example.gbbank.data.crypto_rates.CryptoApi
import com.example.gbbank.data.crypto_rates.CryptoInterceptor
import com.example.gbbank.data.exchange.ExchangeApi
import com.example.gbbank.data.exchange.ExchangeInterceptor
import com.example.gbbank.data.exchange_rates.RatesApi
import com.example.gbbank.data.exchange_rates.RatesInterceptor
import com.example.gbbank.repositories.add_balance_repository.DbAddBalanceRepository
import com.example.gbbank.repositories.add_balance_repository.DbAddBalanceRepositoryImpl
import com.example.gbbank.repositories.change_password_repository.ChangePasswordRepository
import com.example.gbbank.repositories.change_password_repository.ChangePasswordRepositoryImpl
import com.example.gbbank.repositories.crypto_repository.CryptoRepositoryImpl
import com.example.gbbank.repositories.db_add_user_repository.DbAddUserRepository
import com.example.gbbank.repositories.db_add_user_repository.DbAddUserRepositoryImpl
import com.example.gbbank.repositories.edit_profile_photo_repositry.EditProfileRepository
import com.example.gbbank.repositories.edit_profile_photo_repositry.EditProfileRepositoryImpl
import com.example.gbbank.repositories.exchange_repository.ExchangeRepositoryImpl
import com.example.gbbank.repositories.login_repository.LoginRepository
import com.example.gbbank.repositories.login_repository.LoginRepositoryImpl
import com.example.gbbank.repositories.rates_repository.RatesRepositoryImpl
import com.example.gbbank.repositories.register_repository.RegisterRepository
import com.example.gbbank.repositories.register_repository.RegisterRepositoryImpl
import com.example.gbbank.repositories.reset_password_repository.ResetPasswordRepository
import com.example.gbbank.repositories.reset_password_repository.ResetPasswordRepositoryImpl
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
        auth: FirebaseAuth, handler: ResponseHandler, repository: DbAddUserRepositoryImpl
    ): RegisterRepository = RegisterRepositoryImpl(auth, handler, repository)

    @Provides
    @Singleton
    fun provideLoginRepository(
        auth: FirebaseAuth, handler: ResponseHandler
    ): LoginRepository = LoginRepositoryImpl(auth, handler)

    @Provides
    @Singleton
    fun provideResetPasswordRepository(
        auth: FirebaseAuth, handler: ResponseHandler
    ): ResetPasswordRepository = ResetPasswordRepositoryImpl(auth, handler)

    @Provides
    @Singleton
    fun provideAddUserDbRepository(
        handler: ResponseHandler, db: FirebaseDatabase
    ): DbAddUserRepository = DbAddUserRepositoryImpl(handler, db)

    @Provides
    @Singleton
    fun provideAddBalanceRepository(
        auth: FirebaseAuth, db: FirebaseDatabase, handler: ResponseHandler
    ): DbAddBalanceRepository = DbAddBalanceRepositoryImpl(auth, db, handler)

    @Provides
    @Singleton
    fun provideEditProfileRepository(
        handler: ResponseHandler, db: FirebaseDatabase, auth: FirebaseAuth
    ): EditProfileRepository = EditProfileRepositoryImpl(handler, db, auth)

    @Provides
    @Singleton
    fun provideChangePasswordRepository(
        auth: FirebaseAuth, handler: ResponseHandler
    ): ChangePasswordRepository = ChangePasswordRepositoryImpl(auth, handler)

    @Provides
    @Singleton
    fun provideResponseHandler() = ResponseHandler()

    @Provides
    @Singleton
    fun provideCryptoRepository(
        api: CryptoApi,
        handler: ResponseHandler
    ): CryptoRepositoryImpl = CryptoRepositoryImpl(api, handler)

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
    fun provideRatesRepository(
        api: RatesApi,
        handler: ResponseHandler
    ): RatesRepositoryImpl = RatesRepositoryImpl(api, handler)


    @Provides
    @Singleton
    fun provideRatesApi(): RatesApi = Retrofit.Builder()
        .baseUrl("https://test-api.tbcbank.ge")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().apply { addInterceptor(RatesInterceptor()) }.build())
        .build()
        .create(RatesApi::class.java)

    @Provides
    @Singleton
    fun provideExchangeRepository(
        api: ExchangeApi,
        handler: ResponseHandler
    ): ExchangeRepositoryImpl = ExchangeRepositoryImpl(api, handler)

    @Provides
    @Singleton
    fun provideExchangeApi(): ExchangeApi = Retrofit.Builder()
        .baseUrl("https://test-api.tbcbank.ge")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().apply { addInterceptor(ExchangeInterceptor()) }.build())
        .build()
        .create(ExchangeApi::class.java)

}