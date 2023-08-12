package com.islam.task.recipe.presentation.model

import android.view.View

data class RecipeUiState(
    val name: String? = null,
    val category: String? = null,
    val origin: String? = null,
    val tags: List<String>? = null,
    val youtubeUrl: String? = null,
    val instructions: String? = null,
    val dataVisibility:Int = View.VISIBLE
)