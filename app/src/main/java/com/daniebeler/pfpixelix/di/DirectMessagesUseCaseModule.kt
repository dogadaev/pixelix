package com.daniebeler.pfpixelix.di

import com.daniebeler.pfpixelix.domain.repository.DirectMessagesRepository
import com.daniebeler.pfpixelix.domain.usecase.GetConversationsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DirectMessagesUseCaseModule {

    @Provides
    @Singleton
    fun provideGetConversationsUseCase(repository: DirectMessagesRepository): GetConversationsUseCase =
        GetConversationsUseCase(repository)
}