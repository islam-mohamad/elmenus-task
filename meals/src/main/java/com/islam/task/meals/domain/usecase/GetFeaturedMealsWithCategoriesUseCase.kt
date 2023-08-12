package com.islam.task.meals.domain.usecase

import com.islam.task.meals.domain.entity.CategoriesWithFeaturedMealsEntity
import com.islam.task.meals.domain.entity.EmptyListException
import com.islam.task.meals.domain.repository.MealRepository
import javax.inject.Inject

class GetFeaturedMealsWithCategoriesUseCase @Inject constructor(
    private val repository: MealRepository,
    private val getMealsByCategoryUseCase: GetMealsByCategoryUseCase
) {
    suspend operator fun invoke(): CategoriesWithFeaturedMealsEntity {
        val categories = repository.getAllMealCategories()
        if (categories.isEmpty())
            throw EmptyListException()
        val meals = getMealsByCategoryUseCase(categories[0].strCategory?:"")
        return CategoriesWithFeaturedMealsEntity(categories, meals)
    }
}