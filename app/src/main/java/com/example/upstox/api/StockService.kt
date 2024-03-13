package com.example.upstox.api

import com.example.upstox.models.HoldingObject
import retrofit2.http.GET

interface StockService {

    @GET("v3/bde7230e-bc91-43bc-901d-c79d008bddc8")
    suspend fun getStocks(): HoldingObject
}