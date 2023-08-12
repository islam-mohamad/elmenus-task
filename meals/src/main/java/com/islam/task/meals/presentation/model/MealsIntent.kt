package com.islam.task.meals.presentation.model

sealed class MealsIntent {
    object InitializeIntent : MealsIntent()
    data class GetMealsByCategoryIntent(val category: String) : MealsIntent()
    data class NavigateToDetailsIntent(val deepLink:String) : MealsIntent()
}