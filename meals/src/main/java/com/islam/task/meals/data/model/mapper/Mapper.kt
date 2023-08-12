package com.islam.task.meals.data.model.mapper

import com.islam.task.meals.data.model.CategoryModel
import com.islam.task.meals.data.model.MealModel
import com.islam.task.meals.domain.entity.CategoryEntity
import com.islam.task.meals.domain.entity.MealEntity

fun CategoryModel.toEntity() = CategoryEntity(
    idCategory = idCategory,
    strCategory = strCategory,
    strCategoryThumb = strCategoryThumb,
    strCategoryDescription = strCategoryDescription
)

fun MealModel.toEntity() =
    MealEntity(strMeal = strMeal, strMealThumb = strMealThumb, idMeal = idMeal)