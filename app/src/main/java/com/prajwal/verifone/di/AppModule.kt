package com.prajwal.verifone.di

import android.content.Context
import com.prajwal.verifone.data.repo.AnalyticsRepositoryImpl
import com.prajwal.verifone.domain.AnalyticsRepository
import com.prajwal.verifone.domain.GetAnalyticsStatsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAnalyticsRepository(@ApplicationContext context: Context): AnalyticsRepository =
        AnalyticsRepositoryImpl(context)

    @Provides
    fun provideUseCase(repository: AnalyticsRepository) =
        GetAnalyticsStatsUseCase(repository)
}
