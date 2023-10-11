package com.lmkhant.moviedb.data.di

import com.lmkhant.moviedb.data.network.NetworkApi
import com.lmkhant.moviedb.data.repository.MovieRepositoryImpl
import com.lmkhant.moviedb.data.repository.datasource.MovieLocalDataSource
import com.lmkhant.moviedb.data.repository.datasource.MovieRemoteDatasource
import com.lmkhant.moviedb.data.repository.datasourceimpl.MovieRemoteDataSourceImpl
import com.lmkhant.moviedb.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Provides
    @Singleton
    fun provideRemoteDataSource(networkApi: NetworkApi): MovieRemoteDatasource {
        return MovieRemoteDataSourceImpl(networkApi)
    }
    @Provides
    @Singleton
    fun provideMovieRepository(
        movieLocalDataSource: MovieLocalDataSource,
        movieRemoteDatasource: MovieRemoteDatasource
    ): MovieRepository {
        return MovieRepositoryImpl(
            movieRemoteDatasource,
            movieLocalDataSource,
        )
    }
}
