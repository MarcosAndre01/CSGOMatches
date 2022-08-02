package com.example.csgomatches.di

import com.example.csgomatches.data.BASE_URL
import com.example.csgomatches.data.matches.MatchesRepository
import com.example.csgomatches.data.matches.service.MatchesRemoteDataSource
import com.example.csgomatches.data.tournaments.TournamentsRepository
import com.example.csgomatches.data.tournaments.service.TournamentsRemoteDataSource
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
    fun provideMatchesRepository(matchesRemoteDataSource: MatchesRemoteDataSource): MatchesRepository {
        return MatchesRepository(matchesRemoteDataSource)
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