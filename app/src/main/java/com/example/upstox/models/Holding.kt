package com.example.upstox.models

data class Holding(
    val symbol: String,
    val quantity: Int,
    val ltp: Double,
    val avgPrice: Double,
    val close: Int
)

data class HoldingObject(
    val holdingList: List<Holding>
)
