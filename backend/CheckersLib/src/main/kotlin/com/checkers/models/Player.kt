package com.checkers.models

import com.checkers.utlis.initOnce

sealed class Player {
    var playerDirection: PlayerDirection by initOnce()
    var name: String by initOnce()
    var id: Int by initOnce()
}