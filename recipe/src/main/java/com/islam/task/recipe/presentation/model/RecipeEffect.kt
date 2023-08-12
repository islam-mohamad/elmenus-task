package com.islam.task.recipe.presentation.model

sealed class RecipeEffect {
    object ShowLoading : RecipeEffect()
    object HideLoading : RecipeEffect()
    data class ShowError(val errorRes: Int) : RecipeEffect()
}