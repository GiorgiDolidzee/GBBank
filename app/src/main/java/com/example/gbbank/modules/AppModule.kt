package com.example.gbbank.modules

import com.example.gbbank.repositories.RegisterRepository
import com.example.gbbank.utils.ResponseHandler
import com.google.firebase.auth.FirebaseAuth
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
        auth: FirebaseAuth, handler: ResponseHandler)
    : RegisterRepository = RegisterRepository(auth, handler)

    @Provides
    @Singleton
    fun provideResponseHandler() = ResponseHandler()

}