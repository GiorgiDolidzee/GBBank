package com.example.gbbank.modules

import com.example.gbbank.repositories.*
import com.example.gbbank.utils.ResponseHandler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRegisterRepository(
        auth: FirebaseAuth, handler: ResponseHandler, repository: DbAddUserRepository)
    : RegisterRepository = RegisterRepository(auth, handler, repository)

    @Provides
    @Singleton
    fun provideLoginRepository(
        auth: FirebaseAuth, handler: ResponseHandler
    ) : LoginRepository = LoginRepository(auth, handler)

    @Provides
    @Singleton
    fun provideResetPasswordRepository(
        auth: FirebaseAuth, handler: ResponseHandler
    ) : ResetPasswordRepository = ResetPasswordRepository(auth, handler)

    @Provides
    @Singleton
    fun provideAddUserDbRepository(
        handler: ResponseHandler, db: FirebaseDatabase
    ) : DbAddUserRepository = DbAddUserRepository(handler, db)

    @Provides
    @Singleton
    fun provideAddBalanceRepository(
        auth: FirebaseAuth, db: FirebaseDatabase, handler: ResponseHandler
    ) : DbAddBalanceRepository = DbAddBalanceRepository(auth, db, handler)

    @Provides
    @Singleton
    fun provideResponseHandler() = ResponseHandler()

}