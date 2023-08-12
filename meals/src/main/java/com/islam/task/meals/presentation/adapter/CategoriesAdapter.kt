package com.islam.task.meals.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.islam.task.meals.databinding.ItemCategoryBinding
import com.islam.task.meals.presentation.model.CategoryUiState
import com.squareup.picasso.Picasso

class CategoriesAdapter(private val itemClick: (CategoryUiState) -> Unit) :
    ListAdapter<CategoryUiState, CategoriesAdapter.CategoryViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { itemClick(item) }
    }

    class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoryUiState) = with(binding) {
            tvCategory.text = item.strCategory
            Picasso.get().load(item.strCategoryThumb).fit().into(ivCategory)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CategoryUiState>() {
        override fun areItemsTheSame(oldItem: CategoryUiState, newItem: CategoryUiState): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(
            oldItem: CategoryUiState, newItem: CategoryUiState
        ): Boolean {
            return oldItem == newItem
        }
    }
}