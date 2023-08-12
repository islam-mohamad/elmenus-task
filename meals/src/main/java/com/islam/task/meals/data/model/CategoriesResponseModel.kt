package com.islam.task.meals.data.model

import com.google.gson.annotations.SerializedName

data class CategoriesResponseModel(@SerializedName("categories") val categories: List<CategoryModel> = emptyList())