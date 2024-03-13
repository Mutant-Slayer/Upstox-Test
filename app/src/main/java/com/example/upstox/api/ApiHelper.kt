package com.example.upstox.api

import com.example.upstox.models.Holding
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ApiHelper {

    fun getStocks(): Flow<List<Holding>>
}