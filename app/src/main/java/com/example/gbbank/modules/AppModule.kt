package com.example.gbbank.modules

import com.example.gbbank.repositories.DatabaseRepository
import com.example.gbbank.repositories.LoginRepository
import com.example.gbbank.repositories.RegisterRepository
import com.example.gbbank.repositories.ResetPasswordRepository
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
        auth: FirebaseAuth, handler: ResponseHandler, repository: DatabaseRepository)
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
    fun provideDatabaseRepository(
        handler: ResponseHandler, db: FirebaseDatabase
    ) : DatabaseRepository = DatabaseRepository(handler, db)

    @Provides
    @Singleton
    fun provideResponseHandler() = ResponseHandler()

}