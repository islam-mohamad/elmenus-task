package com.islam.task.recipe.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.islam.task.core.base.BaseFragment
import com.islam.task.core.navigation.NavControllerManager
import com.islam.task.recipe.R
import com.islam.task.recipe.databinding.FragmentRecipeBinding
import com.islam.task.recipe.presentation.adapter.RecipeTagsAdapter
import com.islam.task.recipe.presentation.model.RecipeEffect
import com.islam.task.recipe.presentation.model.RecipeIntent
import com.islam.task.recipe.presentation.model.RecipeUiState
import com.islam.task.recipe.presentation.stateholder.RecipeViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class RecipeFragment :
    BaseFragment<FragmentRecipeBinding, RecipeUiState, RecipeEffect, RecipeIntent, RecipeViewModel>() {

    @Inject
    override lateinit var navControllerManager: NavControllerManager

    override val viewModel: RecipeViewModel by viewModels()

    private val mealId by lazy { arguments?.getString("id") }
    private val mealName by lazy { arguments?.getString("name") }

    private val tagsAdapter by lazy { RecipeTagsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mealId?.let { viewModel.sendIntent(RecipeIntent.GetDetails(it)) } ?: run {
            showSnackBar(R.string.meal_not_found)
        }
    }

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentRecipeBinding.inflate(inflater, container, false)

    override fun renderState(ui: RecipeUiState): Unit = with(binding) {
        ui.run {
            youtubeUrl?.let { playVideo(it) }
            name?.let {
                tvTitle.text = it
                tvName.text = it
            }
            tvCategory.text = category
            tvOrigin.text = origin
            tvRecipeContent.text = instructions
            tagsAdapter.submitList(tags)
            recipeLayout.visibility = dataVisibility
        }
    }

    override fun renderEffects(effect: RecipeEffect) = with(binding) {
        when (effect) {
            is RecipeEffect.ShowLoading -> pbLoading.visibility = View.VISIBLE
            is RecipeEffect.HideLoading -> pbLoading.visibility = View.GONE
            is RecipeEffect.ShowError -> showSnackBar(effect.errorRes)
        }
    }

    override fun setup(savedInstanceState: Bundle?) {
        initViews()
        initActions()
    }

    private fun initViews() = with(binding) {
        lifecycle.addObserver(youtubePlayerView)
        rvTags.adapter = tagsAdapter
        tvTitle.text = mealName
    }

    private fun initActions() = with(binding) {
        tvShowMore.setOnClickListener {
            tvRecipeContent.maxLines = Integer.MAX_VALUE
            tvShowMore.visibility = View.GONE
        }
        ivBack.setOnClickListener { navControllerManager.navController?.navigateUp() }
    }

    private fun playVideo(videoUrl: String) {
        if (videoUrl.trim().isEmpty()) return
        binding.youtubePlayerView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = extractVideoIdFromUrl(videoUrl)
                youTubePlayer.loadVideo(videoId ?: "", 0f)
            }
        })
    }

    fun extractVideoIdFromUrl(url: String): String? {
        val pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*"
        val regex = Regex(pattern)
        val matchResult = regex.find(url)
        return matchResult?.value
    }

}