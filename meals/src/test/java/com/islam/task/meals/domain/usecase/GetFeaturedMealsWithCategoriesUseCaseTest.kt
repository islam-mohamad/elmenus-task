package com.islam.task.meals.domain.usecase

import com.islam.task.meals.domain.entity.CategoriesWithFeaturedMealsEntity
import com.islam.task.meals.domain.entity.CategoryEntity
import com.islam.task.meals.domain.entity.EmptyListException
import com.islam.task.meals.domain.entity.MealEntity
import com.islam.task.meals.domain.repository.MealRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever


class GetFeaturedMealsWithCategoriesUseCaseTest {
    @Mock
    private lateinit var repository: MealRepository

    @Mock
    private lateinit var getMealsByCategoryUseCase: GetMealsByCategoryUseCase

    @Mock
    private lateinit var categoryEntity: CategoryEntity

    @Mock
    private lateinit var mealEntity: MealEntity

    private lateinit var getFeaturedMealsWithCategoriesUseCase: GetFeaturedMealsWithCategoriesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getFeaturedMealsWithCategoriesUseCase = GetFeaturedMealsWithCategoriesUseCase(
            repository = repository, getMealsByCategoryUseCase = getMealsByCategoryUseCase
        )
    }

    @Test
    fun `given categories list is empty, when getFeaturedMealsWithCategoriesUseCase invoked then it throws an exception`() = runTest {
        //Arrange
        whenever(repository.getAllMealCategories()).thenReturn(emptyList())

        //Action
        val result = runCatching {
            getFeaturedMealsWithCategoriesUseCase()
        }.onFailure {
            Assert.assertTrue(it is EmptyListException)
        }

        //Assert
        verifyNoInteractions(getMealsByCategoryUseCase)
        Assert.assertTrue(result.isFailure)
    }

    @Test
    fun `given categories list , when getFeaturedMealsWithCategoriesUseCase invoked then return meals list for the first item in categories`() = runTest {
        //Arrange
        whenever(repository.getAllMealCategories()).thenReturn(listOf(categoryEntity))
        whenever(categoryEntity.strCategory).thenReturn(CATEGORY_NAME)
        whenever(getMealsByCategoryUseCase.invoke(CATEGORY_NAME)).thenReturn(listOf(mealEntity))

        //Action
        val result = getFeaturedMealsWithCategoriesUseCase()

        //Assert
        Assert.assertEquals(
            result, CategoriesWithFeaturedMealsEntity(listOf(categoryEntity), listOf(mealEntity))
        )
    }

    companion object {
        private const val CATEGORY_NAME = "CATEGORY_ID"
    }
}