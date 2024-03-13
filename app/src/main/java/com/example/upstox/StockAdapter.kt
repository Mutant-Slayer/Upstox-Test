package com.example.upstox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.upstox.models.Holding

class StockAdapter(
    private val stockList: ArrayList<Holding>
) : ListAdapter<Holding, StockAdapter.HoldingViewHolder>(DiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoldingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stock, parent, false)
        return HoldingViewHolder(view)
    }

    override fun onBindViewHolder(holder: HoldingViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class HoldingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val companyName = view.findViewById<TextView>(R.id.tvCompanyName)
        val ltp = view.findViewById<TextView>(R.id.tvLTP)
        val stocks = view.findViewById<TextView>(R.id.tvNoOfStocks)
        val profitLoss = view.findViewById<TextView>(R.id.tvPL)

        fun bind(item: Holding) {
            companyName.text = item.symbol
            ltp.text = item.ltp.toString()
            stocks.text = item.quantity.toString()
            profitLoss.text =
                ((item.ltp * item.quantity) - (item.avgPrice * item.quantity)).toString()
        }
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Holding>() {
        override fun areItemsTheSame(oldItem: Holding, newItem: Holding): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(oldItem: Holding, newItem: Holding): Boolean {
            return oldItem == newItem
        }
    }

    fun addData(list: List<Holding>) {
        stockList.addAll(list)
    }
}