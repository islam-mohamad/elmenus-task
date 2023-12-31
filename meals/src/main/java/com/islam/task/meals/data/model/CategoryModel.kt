package com.islam.task.meals.data.model

import com.google.gson.annotations.SerializedName

data class CategoryModel(
    @SerializedName("idCategory") val idCategory: String?,
    @SerializedName("strCategory") val strCategory: String,
    @SerializedName("strCategoryThumb") val strCategoryThumb: String?,
    @SerializedName("strCategoryDescription") val strCategoryDescription: String?
)