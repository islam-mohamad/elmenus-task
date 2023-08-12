package com.islam.task.meals.di

import com.islam.task.core.navigation.DeeplinkProcessor
import com.islam.task.meals.presentation.navigation.MealsDeepLinkProcessor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {
    @Binds
    @IntoSet
    abstract fun bindMealsProcessors(
        processor: MealsDeepLinkProcessor
    ): DeeplinkProcessor
}