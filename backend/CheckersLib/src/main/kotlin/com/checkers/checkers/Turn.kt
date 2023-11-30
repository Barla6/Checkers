package com.checkers.checkers

class Turn(private val steps: List<Step>) : Cloneable {
    val boardAfterTurn: Board = steps.last().boardAfterMove
    val trail: List<Coordinate> = steps.map { it.from } + steps.last().to
    val hasEaten = steps.last().hasEaten

    override fun clone(): Any {
        return Turn(steps)
    }

    operator fun plus(step: Step): Turn {
        return Turn(steps + step)
    }
}

