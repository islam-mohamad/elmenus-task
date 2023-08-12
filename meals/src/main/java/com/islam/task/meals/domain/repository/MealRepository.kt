package com.islam.task.meals.domain.repository

import com.islam.task.meals.domain.entity.CategoryEntity
import com.islam.task.meals.domain.entity.MealEntity

interface MealRepository {
    suspend fun getAllMealCategories(): List<CategoryEntity>
    suspend fun getMealsByCategory(category: String): List<MealEntity>
}