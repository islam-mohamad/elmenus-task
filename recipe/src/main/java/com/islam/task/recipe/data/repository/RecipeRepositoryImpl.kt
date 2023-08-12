package com.islam.task.recipe.data.repository

import com.islam.task.core.base.BaseRepository
import com.islam.task.core.di.IODispatcher
import com.islam.task.recipe.data.model.mapper.toEntity
import com.islam.task.recipe.data.source.remote.RecipeApi
import com.islam.task.recipe.domain.entity.RecipeEntity
import com.islam.task.recipe.domain.repository.RecipeRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    @IODispatcher dispatcher: CoroutineDispatcher, private val api: RecipeApi
) : RecipeRepository, BaseRepository(dispatcher) {
    override suspend fun getRecipe(id: String): List<RecipeEntity> =
        runOnIO { api.getRecipe(id).meals.map { it.toEntity() } }
}