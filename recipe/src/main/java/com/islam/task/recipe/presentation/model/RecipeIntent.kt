package com.islam.task.recipe.presentation.model

sealed class RecipeIntent {
    class GetDetails(val id: String) : RecipeIntent()
}