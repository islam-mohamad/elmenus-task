package com.islam.task.meals.presentation.navigation

import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import com.islam.task.core.navigation.DeeplinkProcessor
import com.islam.task.core.navigation.NavControllerManager
import javax.inject.Inject

class MealsDeepLinkProcessor @Inject constructor(
    private val navControllerManager: NavControllerManager
) : DeeplinkProcessor {

    override fun matches(deeplink: String): Boolean {
        return deeplink.contains("/meals_list")
    }

    override fun execute(deeplink: String) {
        val request = NavDeepLinkRequest.Builder.fromUri(deeplink.toUri()).build()
        navControllerManager.navController?.navigate(request)
    }
}