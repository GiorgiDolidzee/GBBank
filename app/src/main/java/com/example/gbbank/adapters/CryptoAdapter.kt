package com.example.gbbank.adapters

import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gbbank.databinding.LayoutCryptoItemBinding
import com.example.gbbank.extensions.setImage
import com.example.gbbank.model.Crypto

class CryptoAdapter(private val cryptos: List<Crypto>) :
    RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {

    inner class CryptoViewHolder(val binding: LayoutCryptoItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
            return CryptoViewHolder(LayoutCryptoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val currentItem = cryptos[position]
        holder.binding.apply {
            ivImage.setImage(currentItem.image)
            tvName.text = currentItem.name
            tvPrice.text = currentItem.currentPrice.toString()
            tvSymbol.text = currentItem.symbol
        }
        d("TAG", "onBind: $currentItem")
    }

    override fun getItemCount(): Int = cryptos.size
}