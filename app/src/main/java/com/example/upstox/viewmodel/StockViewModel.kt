package com.example.upstox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upstox.api.ApiHelper
import com.example.upstox.models.Holding
import com.example.upstox.models.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class StockViewModel(private val apiHelper: ApiHelper) : ViewModel() {

    private val _stockState = MutableStateFlow<Response<List<Holding>>>(Response.Loading)

    val stockState: StateFlow<Response<List<Holding>>> = _stockState

    init {
        fetchStocks()
    }

    private fun fetchStocks() {
        viewModelScope.launch {
            _stockState.value = Response.Loading
            apiHelper.getStocks().flowOn(Dispatchers.IO).catch { e ->
                _stockState.value = Response.Error(e.message.toString())
            }.collect {
                if (it != null) {
                    _stockState.value = Response.Success(it)
                } else {
                    _stockState.value = Response.Error("No Data")
                }
            }
        }
    }
}