package com.islam.task.meals.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.islam.task.meals.databinding.ItemMealBinding
import com.islam.task.meals.presentation.model.MealUiState
import com.squareup.picasso.Picasso

class MealsAdapter(private val itemClick: (MealUiState) -> Unit) :
    ListAdapter<MealUiState, MealsAdapter.MealViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(
            ItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { itemClick(item) }
    }

    class MealViewHolder(private val binding: ItemMealBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MealUiState) = with(binding) {
            tvMealName.text = item.strMeal
            Picasso.get().load(item.strMealThumb).fit().into(ivMeal)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<MealUiState>() {
        override fun areItemsTheSame(oldItem: MealUiState, newItem: MealUiState): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: MealUiState, newItem: MealUiState
        ): Boolean {
            return oldItem == newItem
        }
    }
}