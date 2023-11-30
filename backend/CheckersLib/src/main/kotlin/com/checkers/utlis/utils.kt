package com.checkers.utlis

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

fun rangeBetween(a: Int, b: Int): List<Int> {
    val inclusiveRange = if (a > b) a downTo b else a .. b
    return inclusiveRange.drop(1).dropLast(1)
}

suspend inline fun <T, R> Iterable<T>.asyncMap(scope: CoroutineScope, crossinline block: suspend (T) -> R) =
    map { scope.async { block(it) } }.awaitAll()


suspend inline fun <T, R> Iterable<T>.asyncMapIndexed(scope: CoroutineScope, crossinline block: suspend (Int, T) -> R) =
    mapIndexed {index, it -> scope.async { block(index, it) } }.awaitAll()


fun assert(block: () -> Unit) = try {
    block()
    true
} catch (e: Throwable) {
    false
}