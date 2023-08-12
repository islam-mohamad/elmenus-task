package com.islam.task.recipe.data.model

import com.google.gson.annotations.SerializedName

data class RecipeResponseModel(@SerializedName("meals" ) val meals : List<RecipeModel> = listOf())