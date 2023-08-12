package com.islam.task.meals.presentation.stateholder

import com.islam.task.core.base.BaseViewModel
import com.islam.task.core.di.DefaultDispatcher
import com.islam.task.core.di.MainDispatcher
import com.islam.task.core.navigation.DefaultDeeplinkHandler
import com.islam.task.meals.R
import com.islam.task.meals.domain.entity.EmptyListException
import com.islam.task.meals.domain.usecase.GetFeaturedMealsWithCategoriesUseCase
import com.islam.task.meals.domain.usecase.GetMealsByCategoryUseCase
import com.islam.task.meals.presentation.model.*
import com.islam.task.meals.presentation.model.mapper.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class MealsViewModel @Inject constructor(
    @MainDispatcher mainDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val getFeaturedMealsWithCategoriesUseCase: GetFeaturedMealsWithCategoriesUseCase,
    private val getMealsByCategoryUseCase: GetMealsByCategoryUseCase,
    private val deeplinkHandler: DefaultDeeplinkHandler
) : BaseViewModel<MealsUiState, MealsEffect, MealsIntent>(mainDispatcher, MealsUiState()) {
    override fun transform(intent: MealsIntent) {
        when (intent) {
            is MealsIntent.InitializeIntent -> initialize()
            is MealsIntent.GetMealsByCategoryIntent -> getMealsByCategory(intent.category)
            is MealsIntent.NavigateToDetailsIntent -> deeplinkHandler.process(intent.deepLink)
        }
    }

    private fun initialize() {
        launchBlock(onStart = ::showLoading, onError = ::onError) {
            val result = getFeaturedMealsWithCategoriesUseCase()
            val categories = async(defaultDispatcher) { result.categories.map { it.toUiState() } }
            val meals = async(defaultDispatcher) { result.meals.map { it.toUiState() } }
            updateCategoriesAndMeals(categories.await(), meals.await())
            hideLoading()
        }
    }

    private fun updateCategoriesAndMeals(
        categories: List<CategoryUiState>, meals: List<MealUiState>
    ) {
        setState {
            copy(
                selectedCategory = categories[0].strCategory,
                categoryUiState = categories,
                mealUiState = meals
            )
        }
    }

    private fun getMealsByCategory(category: String) {
        launchBlock(onStart = ::showLoading, onError = {
            onError(it)
            setState { copy(mealUiState = emptyList()) }
        }) {
            val meals = getMealsByCategoryUseCase(category)
            setState {
                copy(selectedCategory = category,
                    mealUiState = meals.map { it.toUiState() })
            }
            hideLoading()
        }
    }

    private fun showLoading() = setEffect { MealsEffect.ShowLoading }
    private fun hideLoading() = setEffect { MealsEffect.HideLoading }
    private fun onError(error: Throwable) {
        hideLoading()
        setEffect { MealsEffect.ShowError(error.map()) }
    }

    private fun Throwable.map() = when (this) {
        is UnknownHostException, is ConnectException, is SocketTimeoutException -> R.string.connection_error
        is HttpException -> R.string.server_down
        is EmptyListException -> R.string.no_categories
        else -> R.string.something_wrong
    }

}