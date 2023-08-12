package com.islam.task.meals.data.repository

import com.islam.task.core.base.BaseRepository
import com.islam.task.core.di.IODispatcher
import com.islam.task.meals.data.model.mapper.toEntity
import com.islam.task.meals.data.source.remote.MealApi
import com.islam.task.meals.domain.entity.CategoryEntity
import com.islam.task.meals.domain.entity.MealEntity
import com.islam.task.meals.domain.repository.MealRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    @IODispatcher dispatcher: CoroutineDispatcher, private val api: MealApi
) : MealRepository, BaseRepository(dispatcher) {
    override suspend fun getAllMealCategories(): List<CategoryEntity> =
        runOnIO { api.getCategories().categories.map { it.toEntity() } }

    override suspend fun getMealsByCategory(category: String): List<MealEntity> =
        runOnIO { api.getMealsByCategory(category).meals.map { it.toEntity() } }
}