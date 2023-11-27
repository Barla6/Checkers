package com.checkers.models

class Step(
    val from: Coordinate,
    val to: Coordinate,
    val hasEaten: Boolean,
    val boardAfterMove: Board,
)