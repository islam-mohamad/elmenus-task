package com.islam.task.meals.di

import com.islam.task.meals.data.repository.MealRepositoryImpl
import com.islam.task.meals.data.source.remote.MealApi
import com.islam.task.meals.domain.repository.MealRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class MealsModule {
    @Binds
    abstract fun bindMealRepository(impl: MealRepositoryImpl): MealRepository

    companion object {
        @Provides
        fun provideUsersApi(retrofit: Retrofit): MealApi = retrofit.create(MealApi::class.java)
    }
}