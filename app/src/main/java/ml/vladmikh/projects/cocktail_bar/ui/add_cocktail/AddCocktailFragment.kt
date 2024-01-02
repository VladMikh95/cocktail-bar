package ml.vladmikh.projects.cocktail_bar.ui.add_cocktail

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ml.vladmikh.projects.cocktail_bar.R
import ml.vladmikh.projects.cocktail_bar.data.local.entities.CocktailLocalDataSource
import ml.vladmikh.projects.cocktail_bar.databinding.FragmentAddCocktailBinding
import ml.vladmikh.projects.cocktail_bar.databinding.FragmentIngredientDialogBinding
import ml.vladmikh.projects.cocktail_bar.util.bindTextTwoWay


@AndroidEntryPoint
class AddCocktailFragment : Fragment() {

    private val viewModel by viewModels<AddCoctailsViewModel>()
    private lateinit var binding: FragmentAddCocktailBinding
    private lateinit var plauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerPermissionListener()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddCocktailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.addIngredient.setOnClickListener{
            showIngredientDialog()
        }

        binding.editTextTitle.bindTextTwoWay(viewModel.editTextTitle, viewLifecycleOwner)

        binding.buttonSave.setOnClickListener{

            if (viewModel.editTextTitle.value == null) {
                binding.textInputLayoutTitle.error = getString(R.string.add_cocktail_title)
            } else {
                binding.textInputLayoutTitle.error = null
            }

            if (binding.editTextDescription.text.toString() == "") {
                binding.textInputLayoutDescription.error = "@string/add_ingredient"

            } else if (binding.editTextRecipe.text.toString() == "") {
                binding.textInputLayoutRecipe.error = "@string/add_ingredient"
            } else {

                findNavController().navigate(R.id.action_addCocktailFragment_to_myCocktailsFragment)
            }
        }

        //При нажатии на imageButtonAddImage реализована возможность добавлять картинку
        binding.imageButtonAddImage.setOnClickListener {
            onCheckMediaPermission()
            Log.i("abc", "123")
        }

        binding.buttonCancel.setOnClickListener {
            findNavController().navigateUp()
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

    //Метод вызывает диалог добавления ингредиентов
    private fun showIngredientDialog() {

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
                builder.dismiss()
            }
            show()
        }
    }

    /*
    private fun selectImage() {
        activity?.let {
            if (ContextCompat.checkSelfPermission(it.applicationContext, android.Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED)
        }
    }*/
    private val changeImage =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                val imgUri = data?.data
                binding.imageButtonAddImage.setImageURI(imgUri)
            }
        }

    private fun onCheckMediaPermission() {
        when {
            context?.let {
                //разрешение уже представлено
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            } == PackageManager.PERMISSION_GRANTED -> {
                val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                changeImage.launch(pickImg)
            }

            //показать обоснование запроса разрешения
            shouldShowRequestPermissionRationale(
                Manifest.permission.CAMERA
            ) -> {

            }

            //разрешение не было запрошено
            else -> {
                plauncher.launch(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
        }
    }

    private fun registerPermissionListener() {
        plauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
           if (isGranted) {
               val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
               changeImage.launch(pickImg)
           } else {
               Toast.makeText(activity, getString(R.string.permission_denited), Toast.LENGTH_SHORT).show()
           }
        }
    }

}