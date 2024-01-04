package ml.vladmikh.projects.cocktail_bar.ui.cocktail_detail

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ml.vladmikh.projects.cocktail_bar.R
import ml.vladmikh.projects.cocktail_bar.databinding.FragmentCocktailDetailBinding

@AndroidEntryPoint
class CocktailDetailFragment : Fragment() {

    lateinit var binding: FragmentCocktailDetailBinding
    private val viewModel by viewModels<CocktailDetailViewModel>()
    private val args: CocktailDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCocktailDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getCocktail(args.idCocktail)
        viewModel.cocktail.observe(this.viewLifecycleOwner) { cocktail ->

            binding.title.text = cocktail.name
            binding.description.text = cocktail.description
            binding.recipe.text =cocktail.recipe
            val adapter = IngredientAdapter(cocktail.ingredients.size)
            binding.recyclerViewIngredients.adapter = adapter

            adapter.submitList(cocktail.ingredients)



            val uri = Uri.parse(cocktail.imagePath)
            binding.imageCocktail.setImageURI(uri)
        }

        binding.buttonDelete.setOnClickListener{
            viewModel.cocktail.value?.let { it1 -> viewModel.deleteCocktail(it1) }
            findNavController().navigate(R.id.action_cocktailDetailFragment_to_myCocktailsFragment)
        }

    }


}