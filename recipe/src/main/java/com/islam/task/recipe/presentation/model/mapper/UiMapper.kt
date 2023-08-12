package com.islam.task.recipe.presentation.model.mapper

import com.islam.task.recipe.domain.entity.RecipeEntity
import com.islam.task.recipe.presentation.model.RecipeUiState

fun RecipeEntity.toUiModel() = RecipeUiState(
    name = strMeal,
    category = strCategory,
    origin = strArea,
    tags = strTags?.split(",")?.filter { it.trim().isNotEmpty() },
    youtubeUrl = strYoutube,
    instructions = strInstructions
)