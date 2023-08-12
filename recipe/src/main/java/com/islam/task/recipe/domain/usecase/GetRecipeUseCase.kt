package com.islam.task.recipe.domain.usecase

import com.islam.task.recipe.domain.repository.RecipeRepository
import javax.inject.Inject

class GetRecipeUseCase @Inject constructor(private val repository: RecipeRepository) {
    suspend operator fun invoke(param: String) = repository.getRecipe(param)[0]
}