package ml.vladmikh.projects.cocktail_bar.ui.my_cocktails

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ml.vladmikh.projects.cocktail_bar.data.local.entities.CocktailLocalDataSource
import ml.vladmikh.projects.cocktail_bar.databinding.CocktailItemBinding

class CocktailsAdapter() : ListAdapter<CocktailLocalDataSource, CocktailsAdapter.CocktailViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<CocktailLocalDataSource>() {
            override fun areItemsTheSame(oldItem: CocktailLocalDataSource, newItem: CocktailLocalDataSource): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CocktailLocalDataSource, newItem: CocktailLocalDataSource): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val viewHolder = CocktailViewHolder(
            CocktailItemBinding.inflate(
                LayoutInflater.from( parent.context),
                parent,
                false
            )
        )
        return viewHolder
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            val action = MyCocktailsFragmentDirections.actionMyCocktailsFragmentToCocktailDetailFragment(getItem(position).id)
            holder.itemView.findNavController().navigate(action)
        }
        holder.bind(getItem(position))
    }

    class CocktailViewHolder(private var binding: CocktailItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(cocktail: CocktailLocalDataSource) {

            binding.textViewCocktail.text = cocktail.name
            binding.imageViewCocktail.setImageURI(Uri.parse(cocktail.imagePath))
        }
    }
}