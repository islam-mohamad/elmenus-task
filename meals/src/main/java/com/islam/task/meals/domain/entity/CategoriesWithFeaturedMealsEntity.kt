package com.islam.task.meals.domain.entity

data class CategoriesWithFeaturedMealsEntity(
    val categories: List<CategoryEntity>,
    val meals: List<MealEntity>
)