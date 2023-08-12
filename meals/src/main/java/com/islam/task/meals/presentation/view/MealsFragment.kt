package com.islam.task.meals.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.islam.task.core.base.BaseFragment
import com.islam.task.core.navigation.NavControllerManager
import com.islam.task.meals.R
import com.islam.task.meals.databinding.FragmentMealsBinding
import com.islam.task.meals.presentation.adapter.CategoriesAdapter
import com.islam.task.meals.presentation.adapter.MealsAdapter
import com.islam.task.meals.presentation.model.MealsEffect
import com.islam.task.meals.presentation.model.MealsIntent
import com.islam.task.meals.presentation.model.MealsUiState
import com.islam.task.meals.presentation.stateholder.MealsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MealsFragment :
    BaseFragment<FragmentMealsBinding, MealsUiState, MealsEffect, MealsIntent, MealsViewModel>() {

    @Inject
    override lateinit var navControllerManager: NavControllerManager

    override val viewModel: MealsViewModel by viewModels()

    private val categoriesAdapter by lazy {
        CategoriesAdapter {category->
            category.strCategory?.let {
                viewModel.sendIntent(MealsIntent.GetMealsByCategoryIntent(it))
            }
        }
    }

    private val mealsAdapter by lazy {
        MealsAdapter {
            viewModel.sendIntent(MealsIntent.NavigateToDetailsIntent("recipedetails://recipe?id=${it.idMeal}&name=${it.strMeal}"))
        }
    }

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMealsBinding.inflate(inflater, container, false)

    override fun renderState(ui: MealsUiState) = with(ui) {
        categoriesAdapter.submitList(categoryUiState)
        mealsAdapter.submitList(mealUiState)
        binding.tvMealsLabel.text = selectedCategory?.let {
            binding.tvMealsLabel.visibility = View.VISIBLE
            getString(R.string.meals, it)
        }
    }

    override fun renderEffects(effect: MealsEffect) = with(binding) {
        when (effect) {
            is MealsEffect.ShowLoading -> pbLoading.visibility = View.VISIBLE
            is MealsEffect.HideLoading -> pbLoading.visibility = View.GONE
            is MealsEffect.ShowError -> showSnackBar(effect.errorRes)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.sendIntent(MealsIntent.InitializeIntent)
    }
    override fun setup(savedInstanceState: Bundle?) {
        initViews()
    }

    private fun initViews() = with(binding) {
        rvCategories.adapter = categoriesAdapter
        rvMeals.adapter = mealsAdapter
    }

}