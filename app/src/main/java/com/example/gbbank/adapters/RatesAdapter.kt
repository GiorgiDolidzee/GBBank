package com.example.gbbank.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gbbank.databinding.LayoutRatesItemBinding
import com.example.gbbank.model.Rates

class RatesAdapter(private val rates: Rates?) :
    RecyclerView.Adapter<RatesAdapter.RatesViewHolder>() {
    inner class RatesViewHolder(val binding: LayoutRatesItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesViewHolder {
        return RatesViewHolder(
            LayoutRatesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RatesViewHolder, position: Int) {
            val commercialRate = rates?.commercialRatesList
            holder.binding.apply {
                tvFrom.text = rates?.base
                tvTo.text = commercialRate?.get(position)?.currency.toString()
                tvBuyPrice.text = commercialRate?.get(position)?.buy.toString()
                tvSellPrice.text = commercialRate?.get(position)?.sell.toString()
            }

    }

    override fun getItemCount(): Int = rates!!.commercialRatesList!!.size
}