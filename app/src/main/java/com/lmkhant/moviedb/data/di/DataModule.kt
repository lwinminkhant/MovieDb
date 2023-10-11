package com.lmkhant.moviedb.data.di

import android.app.Application
import androidx.room.Room
import com.lmkhant.moviedb.data.local.MovieDatabase
import com.lmkhant.moviedb.data.repository.datasource.MovieLocalDataSource
import com.lmkhant.moviedb.data.repository.datasourceimpl.MovieLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideMovieDatabase(app: Application): MovieDatabase {
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            MovieDatabase.DATABASE_NAME

        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieLocalDataSource(movieDatabase: MovieDatabase): MovieLocalDataSource {
        return MovieLocalDataSourceImpl(movieDatabase.movieDao())
    }
}