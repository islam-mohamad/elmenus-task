package com.islam.task.recipe.presentation.stateholder

import android.view.View
import com.islam.task.core.base.BaseViewModel
import com.islam.task.core.di.MainDispatcher
import com.islam.task.recipe.R
import com.islam.task.recipe.domain.usecase.GetRecipeUseCase
import com.islam.task.recipe.presentation.model.RecipeEffect
import com.islam.task.recipe.presentation.model.RecipeIntent
import com.islam.task.recipe.presentation.model.RecipeUiState
import com.islam.task.recipe.presentation.model.mapper.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    @MainDispatcher mainDispatcher: CoroutineDispatcher,
    private val getRecipeUseCase: GetRecipeUseCase
) : BaseViewModel<RecipeUiState, RecipeEffect, RecipeIntent>(mainDispatcher, RecipeUiState(dataVisibility = View.GONE)) {
    override fun transform(intent: RecipeIntent) {
        when(intent){
           is  RecipeIntent.GetDetails -> getRecipe(intent.id)
        }
    }

    private fun getRecipe(id: String) {
        launchBlock(onStart = ::showLoading, onError = ::onError) {
           val recipe =  getRecipeUseCase(id)
            setState {
               recipe.toUiModel()
            }
            hideLoading()
        }
    }

    private fun showLoading() = setEffect { RecipeEffect.ShowLoading }
    private fun hideLoading() = setEffect { RecipeEffect.HideLoading }
    private fun onError(error: Throwable) {
        hideLoading()
        setEffect { RecipeEffect.ShowError(error.map()) }
    }

    private fun Throwable.map() = when (this) {
        is UnknownHostException, is ConnectException, is SocketTimeoutException -> R.string.connection_error
        is HttpException -> R.string.server_down
        else -> R.string.something_wrong
    }
}