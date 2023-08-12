package com.islam.task.meals.presentation.model

data class MealsUiState(
    val selectedCategory: String? = null,
    val categoryUiState: List<CategoryUiState> = emptyList(),
    val mealUiState: List<MealUiState> = emptyList()
)

data class CategoryUiState(
    val idCategory: String? = null,
    val strCategory: String? = null,
    val strCategoryThumb: String? = null
)

data class MealUiState(
    val strMeal: String? = null, val strMealThumb: String? = null, val idMeal: String? = null
)