package com.example.upstox.api

import android.util.Log
import com.example.upstox.models.Holding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApiHelperImpl(private val stockService: StockService) : ApiHelper {

    override fun getStocks(): Flow<List<Holding>> {
        return flow {
            Log.d("Anas",stockService.getStocks().toString())
            emit(stockService.getStocks().holdingList)
        }
    }
}