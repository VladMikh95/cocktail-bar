package ml.vladmikh.projects.cocktail_bar.ui.add_cocktail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import ml.vladmikh.projects.cocktail_bar.R
import ml.vladmikh.projects.cocktail_bar.data.local.entities.CocktailLocalDataSource
import ml.vladmikh.projects.cocktail_bar.databinding.FragmentAddCocktailBinding
import ml.vladmikh.projects.cocktail_bar.databinding.FragmentIngredientDialogBinding


@AndroidEntryPoint
class AddCocktailFragment : Fragment() {

    private val viewModel by viewModels<AddCoctailsViewModel>()
    lateinit var binding: FragmentAddCocktailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddCocktailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.addIngredient.setOnClickListener{
            showIngredientDialog {
                if (it != null) {
                    //viewModel.addIngredient(it)
                }
            }
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

        viewModel.listIngredients.observe(viewLifecycleOwner) { ingredients ->

            binding.chipGroup.removeAllViews()
            if (ingredients.isNotEmpty()) {

                for(ingredient in ingredients) {
                    val chip = Chip(context).apply {
                        text = ingredient.trim()
                    }
                    chip.isCloseIconVisible = true
                    chip.setOnCloseIconClickListener {
                        binding.chipGroup.removeView(chip)
                    }
                    binding.chipGroup.addView(chip)
                }
            }
        }
    }

    private fun addIngredient(ingredient: String) {
        val chip = Chip(context).apply {
            text = ingredient
        }
        chip.isCloseIconVisible = true
        chip.setOnCloseIconClickListener {
            binding.chipGroup.removeView(chip)
        }
        binding.chipGroup.addView(chip)
    }

    //Метод вызывает диалог добавления ингредиентов
    private fun showIngredientDialog(ingredient: (String?) -> Unit) {

        val builder = AlertDialog.Builder(requireContext()).create()
        val dialogBinding: FragmentIngredientDialogBinding = FragmentIngredientDialogBinding.inflate(layoutInflater)

        with(builder) {
            setView(dialogBinding.root)
            setCancelable(false)
            dialogBinding.editTextIngredient.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (s?.trim()?.isNotEmpty() == true) dialogBinding.textInputLayoutIngredient.error = null
                    else dialogBinding.textInputLayoutIngredient.error = "Add title"
                }
            })

            dialogBinding.buttonAdd.setOnClickListener {
                if (dialogBinding.editTextIngredient.text?.trim()?.isNotEmpty() == true) {
                    viewModel.addIngredient((dialogBinding.editTextIngredient.text.toString()))
                    builder.dismiss()
                }

            }
            dialogBinding.buttonCancel.setOnClickListener {
                ingredient(null)
                builder.dismiss()
            }
            show()
        }
    }

}