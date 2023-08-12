package com.islam.task.core.navigation

import javax.inject.Inject


class DefaultDeeplinkHandler @Inject constructor(
    private val processors: Set<@JvmSuppressWildcards DeeplinkProcessor>
) : DeeplinkHandler {
    override fun process(deeplink: String): Boolean {
        processors.forEach {
            if (it.matches(deeplink)) {
                it.execute(deeplink)
                return true
            }
        }
        return false
    }
}