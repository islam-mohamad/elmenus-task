package com.islam.task.recipe.di

import com.islam.task.recipe.data.repository.RecipeRepositoryImpl
import com.islam.task.recipe.data.source.remote.RecipeApi
import com.islam.task.recipe.domain.repository.RecipeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class RecipeModule {
    @Binds
    abstract fun bindRecipeRepository(impl: RecipeRepositoryImpl): RecipeRepository

    companion object {
        @Provides
        fun provideRecipeApi(retrofit: Retrofit): RecipeApi = retrofit.create(RecipeApi::class.java)
    }
}