package com.islam.task.recipe.di

import com.islam.task.core.navigation.DeeplinkProcessor
import com.islam.task.recipe.presentation.navigation.RecipeDeepLinkProcessor
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
    abstract fun bindRecipeProcessors(
        processor: RecipeDeepLinkProcessor
    ): DeeplinkProcessor
}