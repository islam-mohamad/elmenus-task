package com.islam.task.meals.presentation.model

sealed class MealsEffect {
    object ShowLoading : MealsEffect()
    object HideLoading : MealsEffect()
    data class ShowError(val errorRes: Int) : MealsEffect()
}