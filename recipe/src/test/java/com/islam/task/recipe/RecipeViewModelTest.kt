package com.islam.task.recipe

import android.view.View
import com.islam.task.core.test.test
import com.islam.task.recipe.domain.entity.RecipeEntity
import com.islam.task.recipe.domain.usecase.GetRecipeUseCase
import com.islam.task.recipe.presentation.model.RecipeEffect
import com.islam.task.recipe.presentation.model.RecipeIntent
import com.islam.task.recipe.presentation.model.RecipeUiState
import com.islam.task.recipe.presentation.stateholder.RecipeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class RecipeViewModelTest {
    private lateinit var viewModel: RecipeViewModel

    @Mock
    private lateinit var getRecipeUseCase: GetRecipeUseCase
    private val testScheduler = TestCoroutineScheduler()
    private lateinit var dispatcher: TestDispatcher

    @Mock
    private lateinit var recipeEntity: RecipeEntity

    @Mock
    private lateinit var recipeUiState: RecipeUiState

    private val defaultRecipeUiState: RecipeUiState by lazy { RecipeUiState(dataVisibility = View.GONE) }

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        viewModel =
            RecipeViewModel(mainDispatcher = dispatcher, getRecipeUseCase = getRecipeUseCase)
    }

    @Test
    fun `given GetDetails intent,  getRecipeUseCase should be invoked and return success`() =
        runTest {
            //Arrange
            Mockito.`when`(getRecipeUseCase.invoke(Mockito.anyString())).thenReturn(recipeEntity)
            val observer = viewModel.effect.test(this)

            //Action
            viewModel.sendIntent(RecipeIntent.GetDetails(MEAL_ID))
            advanceUntilIdle()

            //Assert
            observer.assertValuesAndFinish(
                RecipeEffect.ShowLoading,
                RecipeEffect.HideLoading,
            )
            assertEquals(viewModel.uiState.value, recipeUiState)
        }

    @Test
    fun `given GetDetails intent,  and if getRecipeUseCase throws an exception it should return Error`() =
        runTest {
            //Arrange
            Mockito.`when`(getRecipeUseCase.invoke(Mockito.anyString()))
                .thenThrow(RuntimeException())

            val observer = viewModel.effect.test(this)

            //Action
            viewModel.sendIntent(RecipeIntent.GetDetails(MEAL_ID))
            advanceUntilIdle()

            //Assert
            observer.assertValuesAndFinish(
                RecipeEffect.ShowLoading,
                RecipeEffect.HideLoading,
                RecipeEffect.ShowError(R.string.something_wrong)
            )

            assertEquals(viewModel.uiState.value, defaultRecipeUiState)
        }

    companion object {
        private const val MEAL_ID = "MEAL_ID"
    }
}