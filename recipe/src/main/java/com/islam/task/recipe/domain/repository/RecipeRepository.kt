package com.islam.task.recipe.domain.repository

import com.islam.task.recipe.domain.entity.RecipeEntity

interface RecipeRepository {
    suspend fun getRecipe(id:String):List<RecipeEntity>
}