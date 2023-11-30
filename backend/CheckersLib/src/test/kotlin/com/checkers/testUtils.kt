package com.checkers

import kotlin.test.assertEquals
import kotlin.test.assertTrue

fun <T: Any?> assertEqualListsIgnoreOrder(expected: List<T>, result: List<T>) {

    assertEquals(expected.size, result.size)

    val mutableResult = result.toMutableList()

    expected.forEach { expectedElement ->
        assertTrue(mutableResult.remove(expectedElement))
    }

}

fun <T: Any?> assertSuccess(result: Result<T>) {
    assertTrue(result.isSuccess, "Expected success, but got $result")
}

fun <T: Any?> assertFailure(result: Result<T>) {
    assertTrue(result.isFailure, "Expected failure, but got $result")
}