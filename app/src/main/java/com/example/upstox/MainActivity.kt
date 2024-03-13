package com.example.upstox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.upstox.api.RetrofitBuilder
import com.example.upstox.viewmodel.StockViewModel
import com.example.upstox.viewmodel.ViewModelFactory
import com.example.upstox.api.ApiHelperImpl
import com.example.upstox.models.Holding
import com.example.upstox.models.Response
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: StockViewModel
    private lateinit var stockAdapter: StockAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvStocks)

        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        stockAdapter = StockAdapter(arrayListOf())
        recyclerView.adapter = stockAdapter
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stockState.collect {
                    when (it) {
                        is Response.Success -> {
                            renderList(it.data)
                            recyclerView.visibility = View.VISIBLE
                        }

                        is Response.Loading -> {
                            recyclerView.visibility = View.GONE
                        }

                        is Response.Error -> {
                            Log.d("Anas", it.message)
                            Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun renderList(list: List<Holding>) {
        stockAdapter.addData(list)
        stockAdapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                ApiHelperImpl(RetrofitBuilder.apiService)
            )
        )[StockViewModel::class.java]
    }
}