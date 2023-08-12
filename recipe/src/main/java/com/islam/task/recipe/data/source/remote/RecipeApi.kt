package com.islam.task.recipe.data.source.remote

import com.islam.task.recipe.data.model.RecipeResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {
    @GET("lookup.php")
    suspend fun getRecipe(@Query("i")mealId:String): RecipeResponseModel
}