package com.islam.task.meals.presentation.model.mapper

import com.islam.task.meals.domain.entity.CategoryEntity
import com.islam.task.meals.domain.entity.MealEntity
import com.islam.task.meals.presentation.model.CategoryUiState
import com.islam.task.meals.presentation.model.MealUiState

fun CategoryEntity.toUiState() = CategoryUiState(
    idCategory = idCategory,
    strCategory = strCategory,
    strCategoryThumb = strCategoryThumb
)

fun MealEntity.toUiState() =
    MealUiState(strMeal = strMeal, strMealThumb = "$strMealThumb/preview", idMeal = idMeal)