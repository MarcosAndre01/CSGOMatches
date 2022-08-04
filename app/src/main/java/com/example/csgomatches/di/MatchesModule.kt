package com.example.csgomatches.di

import android.app.Application
import androidx.room.Room
import com.example.csgomatches.data.BASE_URL
import com.example.csgomatches.data.matches.MatchesRepository
import com.example.csgomatches.data.matches.remote.MatchesRemoteDataSource
import com.example.csgomatches.detail.data.tournaments.TournamentsRepository
import com.example.csgomatches.detail.data.tournaments.service.TournamentsRemoteDataSource
import com.example.csgomatches.data.matches.local.MatchesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MatchesModule {
    @Provides
    @Singleton
    fun provideMatchesRemoteDataSource(): MatchesRemoteDataSource {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MatchesRemoteDataSource::class.java)
    }

    @Provides
    @Singleton
    fun provideMatchesDatabase(app: Application): MatchesDatabase {
        return Room.databaseBuilder(app,
            MatchesDatabase::class.java,
            "matches.dbdb"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMatchesRepository(matchesDatabase: MatchesDatabase, matchesRemoteDataSource: MatchesRemoteDataSource): MatchesRepository {
        return MatchesRepository(matchesDatabase, matchesRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideTournamentsRemoteDataSource(): TournamentsRemoteDataSource {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TournamentsRemoteDataSource::class.java)
    }

    @Provides
    @Singleton
    fun provideTournamentsRepository(tournamentsRemoteDataSource: TournamentsRemoteDataSource): TournamentsRepository {
        return TournamentsRepository(tournamentsRemoteDataSource)
    }
}