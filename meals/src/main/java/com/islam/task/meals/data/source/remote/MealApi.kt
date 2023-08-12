package com.islam.task.meals.data.source.remote

import com.islam.task.meals.data.model.CategoriesResponseModel
import com.islam.task.meals.data.model.MealsResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponseModel

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): MealsResponseModel
}