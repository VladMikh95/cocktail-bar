package ml.vladmikh.projects.cocktail_bar.ui.ingredient_dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ml.vladmikh.projects.cocktail_bar.R
import ml.vladmikh.projects.cocktail_bar.databinding.FragmentIngredientDialogBinding
import ml.vladmikh.projects.cocktail_bar.ui.add_cocktail.AddCocktailFragment

@AndroidEntryPoint
class IngredientDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentIngredientDialogBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentIngredientDialogBinding.inflate(inflater)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCancel.setOnClickListener {
            findNavController().navigate(R.id.action_ingredientDialogFragment_to_addCocktailFragment)
        }

        binding.buttonAdd.setOnClickListener {

            if (binding.editTextIngredient.text.toString() == "") {
                binding.textInputLayoutIngredient.error = "AddIngredient"
            } else {
                val action =
                    IngredientDialogFragmentDirections.actionIngredientDialogFragmentToAddCocktailFragment(
                        binding.editTextIngredient.text.toString()
                    )
                findNavController().navigate(action)
            }

        }

    }

    override fun getTheme() = R.style.AppTheme_Alert
}