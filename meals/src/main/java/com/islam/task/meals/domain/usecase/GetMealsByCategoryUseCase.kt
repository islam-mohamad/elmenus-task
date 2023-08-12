package com.islam.task.meals.domain.usecase

import com.islam.task.meals.domain.repository.MealRepository
import javax.inject.Inject

class GetMealsByCategoryUseCase @Inject constructor(private val repository: MealRepository) {
    suspend operator fun invoke(param: String) = repository.getMealsByCategory(param)
}