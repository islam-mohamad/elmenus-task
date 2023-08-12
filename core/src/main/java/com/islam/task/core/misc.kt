package com.islam.task.core

typealias Action = () -> Unit
typealias Producer<T> = () -> T
typealias Reducer<T> = T.() -> T
typealias Consumer<T> = (T) -> Unit
