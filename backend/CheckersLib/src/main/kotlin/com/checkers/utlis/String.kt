package com.checkers.utlis

const val red = "\u001b[31m"
const val reset = "\u001b[0m"
const val blue = "\u001b[34m"

fun redString(string: String): String {
    return red + string + reset
}

fun blueString(string: String): String {
    return blue + string + reset
}

