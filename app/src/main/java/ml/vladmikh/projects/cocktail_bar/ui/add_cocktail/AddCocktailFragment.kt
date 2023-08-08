package ml.vladmikh.projects.cocktail_bar.ui.add_cocktail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import ml.vladmikh.projects.cocktail_bar.R
import ml.vladmikh.projects.cocktail_bar.data.local.entities.CocktailLocalDataSource
import ml.vladmikh.projects.cocktail_bar.databinding.FragmentAddCocktailBinding
import ml.vladmikh.projects.cocktail_bar.databinding.FragmentMyCocktailsBinding
import ml.vladmikh.projects.cocktail_bar.ui.ingredient_dialog.IngredientDialogFragmentDirections

@AndroidEntryPoint
class AddCocktailFragment : Fragment() {

    private val viewModel by viewModels<AddCoctailsViewModel>()
    lateinit var binding: FragmentAddCocktailBinding
    private val args: AddCocktailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCocktailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.addIngredient.setOnClickListener{
            findNavController().navigate(R.id.action_addCocktailFragment_to_ingredientDialogFragment)
        }

        binding.buttonSave.setOnClickListener{
            if (binding.editTextTitle.text.toString() == "") {
                binding.textInputLayoutTitle.error = "AddIngredient"
            } else if (binding.editTextDescription.text.toString() == "") {
                binding.textInputLayoutDescription.error = "AddIngredient"

            } else if (binding.editTextRecipe.text.toString() == "") {
                binding.textInputLayoutRecipe.error = "AddIngredient"
            } else {
                val coctail = CocktailLocalDataSource(0,
                        binding.editTextTitle.text.toString(),
                        "", //Путь к изображению, не реализованно добавление пути изображения
                        binding.editTextDescription.text.toString(),
                        ArrayList<String>(), //Нужно добавлять список ингредиентов, не реализовано добавление
                        binding.editTextRecipe.text.toString()
                    )
                viewModel.insertCocktail(coctail)
                findNavController().navigate(R.id.action_addCocktailFragment_to_myCocktailsFragment)
            }
        }

        binding.buttonCancel.setOnClickListener {
            findNavController().navigate(R.id.action_addCocktailFragment_to_myCocktailsFragment)
        }

        //if (args.ingredient != "") {
         //  viewModel.addIngredient(args.ingredient)
        //}

        viewModel.ingredients.observe(viewLifecycleOwner) { ingredients ->
            for (index in ingredients) {
                val chip = Chip(binding.chipGroup.context)
                chip.text= index
                chip.isClickable = true
                chip.isCheckable = true
                binding.chipGroup.addView(chip)
            }
        }



    }

}