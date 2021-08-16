package com.app.rickandmortyapp.di

import com.app.rickandmortyapp.data.network.ApiService
import com.app.rickandmortyapp.data.remote.PagingDataSource
import com.app.rickandmortyapp.data.remote.RemoteDataSource
import com.app.rickandmortyapp.data.repository.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        apiService: ApiService,
    ): RemoteDataSource {
        return RemoteDataSource(apiService)
    }

    @Provides
    @Singleton
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
    ): RemoteRepository {
        return RemoteRepository(remoteDataSource)
    }

    @Provides
    @Singleton
    fun providePagingDataSource(
        apiService: ApiService
    ): PagingDataSource {
        return PagingDataSource(apiService)
    }
}