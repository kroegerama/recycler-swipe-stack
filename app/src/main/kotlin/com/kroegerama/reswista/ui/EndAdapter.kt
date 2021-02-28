package com.kroegerama.reswista.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kroegerama.kaiteki.onClick
import com.kroegerama.reswista.databinding.ItemEndBinding

class EndAdapter(
    private val refillClick: () -> Unit
) : RecyclerView.Adapter<EndAdapter.EndViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EndViewHolder(ItemEndBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: EndViewHolder, position: Int) {
        holder.binding.btnRefill.onClick { refillClick() }
    }

    override fun getItemCount(): Int = 1

    class EndViewHolder(val binding: ItemEndBinding) : RecyclerView.ViewHolder(binding.root)
}
