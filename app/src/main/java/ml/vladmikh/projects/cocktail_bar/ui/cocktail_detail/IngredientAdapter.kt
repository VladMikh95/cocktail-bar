package ml.vladmikh.projects.cocktail_bar.ui.cocktail_detail

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ml.vladmikh.projects.cocktail_bar.databinding.IngredientItemBinding

class IngredientAdapter(val countIngredients: Int) : ListAdapter<String, IngredientAdapter.IngredientViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder(
            IngredientItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {

        //У последнего элемента ingredient_item скрываем линию, чтобы линии были только между словами ингредиентов
        if (position == (countIngredients - 1)) {
            holder.line.visibility = View.GONE
        }

        holder.bind(getItem(position))
    }

    class IngredientViewHolder(private var binding: IngredientItemBinding): RecyclerView.ViewHolder(binding.root) {

        val line = binding.line
        fun bind(ingredient: String) {
            Log.i("abc", ingredient)
            binding.textViewIngredient.text = ingredient
        }
    }
}