package com.islam.task.core.navigation

interface DeeplinkHandler {
    fun process(deeplink: String): Boolean
}