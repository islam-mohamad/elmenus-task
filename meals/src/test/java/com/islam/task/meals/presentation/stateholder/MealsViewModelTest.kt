package com.islam.task.meals.presentation.stateholder

import com.islam.task.core.navigation.DefaultDeeplinkHandler
import com.islam.task.core.test.test
import com.islam.task.meals.R
import com.islam.task.meals.domain.entity.CategoriesWithFeaturedMealsEntity
import com.islam.task.meals.domain.entity.CategoryEntity
import com.islam.task.meals.domain.entity.EmptyListException
import com.islam.task.meals.domain.entity.MealEntity
import com.islam.task.meals.domain.usecase.GetFeaturedMealsWithCategoriesUseCase
import com.islam.task.meals.domain.usecase.GetMealsByCategoryUseCase
import com.islam.task.meals.presentation.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class MealsViewModelTest {

    private val testScheduler = TestCoroutineScheduler()
    private lateinit var mainDispatcher: TestDispatcher
    private lateinit var defaultDispatcher: TestDispatcher

    private lateinit var viewModel: MealsViewModel

    @Mock
    private lateinit var getFeaturedMealsWithCategoriesUseCase: GetFeaturedMealsWithCategoriesUseCase

    @Mock
    private lateinit var getMealsByCategoryUseCase: GetMealsByCategoryUseCase

    @Mock
    private lateinit var deeplinkHandler: DefaultDeeplinkHandler

    @Mock
    private lateinit var categoryEntity: CategoryEntity

    @Mock
    private lateinit var mealEntity: MealEntity

    private val categoriesWithFeaturedMealsEntity by lazy {
        CategoriesWithFeaturedMealsEntity(
            listOf(categoryEntity), listOf(mealEntity)
        )
    }

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        mainDispatcher = UnconfinedTestDispatcher(testScheduler)
        defaultDispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(mainDispatcher)
        viewModel = MealsViewModel(
            mainDispatcher = mainDispatcher,
            defaultDispatcher = defaultDispatcher,
            getFeaturedMealsWithCategoriesUseCase = getFeaturedMealsWithCategoriesUseCase,
            getMealsByCategoryUseCase = getMealsByCategoryUseCase,
            deeplinkHandler = deeplinkHandler
        )
    }

    @Test
    fun `given InitializeIntent intent, then categoriesWithFeaturedMealsEntity should be invoked and initialize categories and meals`() =
        runTest {
            //Arrange
            whenever(getFeaturedMealsWithCategoriesUseCase.invoke()).thenReturn(
                    categoriesWithFeaturedMealsEntity
                )
            whenever(categoryEntity.strCategory).thenReturn(CATEGORY_NAME)
            val effectsObserver = viewModel.effect.test(this)

            //Action
            viewModel.sendIntent(MealsIntent.InitializeIntent)
            advanceUntilIdle()

            //Assert
            effectsObserver.assertValuesAndFinish(
                MealsEffect.ShowLoading, MealsEffect.HideLoading
            )

            Assert.assertEquals(
                viewModel.uiState.value, MealsUiState(
                    selectedCategory = CATEGORY_NAME,
                    mealUiState = listOf(MealUiState()),
                    categoryUiState = listOf(CategoryUiState(strCategory = CATEGORY_NAME))
                )
            )
        }

    @Test
    fun `given InitializeIntent intent, then categoriesWithFeaturedMealsEntity should be invoked and when there is no categories it throws an exception `() =
        runTest {
            //Arrange
            whenever(getFeaturedMealsWithCategoriesUseCase.invoke()).thenThrow(EmptyListException())
            val effectsObserver = viewModel.effect.test(this)

            //Action
            viewModel.sendIntent(MealsIntent.InitializeIntent)
            advanceUntilIdle()

            //Assert
            effectsObserver.assertValuesAndFinish(
                MealsEffect.ShowLoading,
                MealsEffect.HideLoading,
                MealsEffect.ShowError(R.string.no_categories)
            )
        }

    @Test
    fun `given GetMealsByCategoryIntent intent, then getMealsByCategoryUseCase should be invoked and update meals list`() =
        runTest {
            //Arrange
            whenever(getMealsByCategoryUseCase.invoke(Mockito.anyString())).thenReturn(listOf(mealEntity))
            val effectsObserver = viewModel.effect.test(this)
            //Action
            viewModel.sendIntent(MealsIntent.GetMealsByCategoryIntent(CATEGORY_NAME))
            advanceUntilIdle()
//            Assert
            effectsObserver.assertValuesAndFinish(
                MealsEffect.ShowLoading, MealsEffect.HideLoading
            )
            Assert.assertEquals(
                viewModel.uiState.value, MealsUiState(
                    selectedCategory = CATEGORY_NAME,
                    mealUiState = listOf(MealUiState())
                )
            )
        }

    @Test
    fun `given NavigateToDetailsIntent intent, then deeplinkHandler should process the deeplink`() =
        runTest {
            //Action
            viewModel.sendIntent(MealsIntent.NavigateToDetailsIntent(DEEP_LINK))
            advanceUntilIdle()
//            Assert
            verify(deeplinkHandler).process(Mockito.anyString())
        }

    companion object {
        private const val CATEGORY_NAME = "CATEGORY_NAME"
        private const val DEEP_LINK = "DEEP_LINK"
        private const val POSITION = 0
    }
}