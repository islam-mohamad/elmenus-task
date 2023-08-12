package com.islam.task.core

object ElMenusKeys {

    // Shared keys
    external fun geApiUrl(): String

    external fun geApiKey(): String

    init {
        System.loadLibrary("main")
    }
}
