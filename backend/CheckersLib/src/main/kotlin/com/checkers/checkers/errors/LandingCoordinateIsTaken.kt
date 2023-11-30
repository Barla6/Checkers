package com.checkers.checkers.errors

import com.checkers.checkers.Coordinate

class LandingCoordinateIsTaken private constructor(message: String?) : Throwable(message) {

    constructor(landingCoordinate: Coordinate):
            this("Landing coordinate: $landingCoordinate is taken")
}