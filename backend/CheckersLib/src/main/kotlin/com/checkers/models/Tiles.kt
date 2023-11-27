package com.checkers.models

typealias Tiles = List<List<Tile>>

fun Tiles.clone(): Tiles {
    return this.map { row ->
        row.map { tile ->
            tile.clone()
        }
    }
}

