package com.islam.task.recipe.presentation.navigation

import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import com.islam.task.core.navigation.DeeplinkProcessor
import com.islam.task.core.navigation.NavControllerManager
import javax.inject.Inject

class RecipeDeepLinkProcessor @Inject constructor(
    private val navControllerManager: NavControllerManager
) : DeeplinkProcessor {

    override fun matches(deeplink: String): Boolean {
        return deeplink.contains("recipedetails")
    }

    override fun execute(deeplink: String) {
        val request = NavDeepLinkRequest.Builder.fromUri(deeplink.toUri()).build()
        navControllerManager.navController?.navigate(request)
    }
}