package com.daniebeler.pixelix.di

import com.daniebeler.pixelix.domain.repository.CountryRepository
import com.daniebeler.pixelix.domain.repository.StorageRepository
import com.daniebeler.pixelix.domain.usecase.GetTrendingAccountsUseCase
import com.daniebeler.pixelix.domain.usecase.GetTrendingHashtagsUseCase
import com.daniebeler.pixelix.domain.usecase.GetTrendingPostsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class TrendingUseCaseModule {

    @Provides
    @Singleton
    fun provideGetTrendingPostsUseCase(
        repository: CountryRepository, storageRepository: StorageRepository
    ): GetTrendingPostsUseCase = GetTrendingPostsUseCase(repository, storageRepository)

    @Provides
    @Singleton
    fun provideGetTrendingAccountsUseCase(repository: CountryRepository): GetTrendingAccountsUseCase =
        GetTrendingAccountsUseCase(repository)

    @Provides
    @Singleton
    fun provideGetTrendingHashtagsUseCase(repository: CountryRepository): GetTrendingHashtagsUseCase =
        GetTrendingHashtagsUseCase(repository)
}