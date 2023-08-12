package com.islam.task.meals.data.model

import com.google.gson.annotations.SerializedName

class MealsResponseModel(@SerializedName("meals") val meals: List<MealModel> = emptyList())