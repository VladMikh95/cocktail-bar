package ml.vladmikh.projects.cocktail_bar.ui.add_cocktail

import android.net.Uri
import android.provider.DocumentsContract
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ml.vladmikh.projects.cocktail_bar.data.local.entities.CocktailLocalDataSource
import ml.vladmikh.projects.cocktail_bar.data.repository.CocktailRepository
import javax.inject.Inject

@HiltViewModel
class AddCoctailsViewModel @Inject constructor(
    private val repository: CocktailRepository
): ViewModel() {

    private var _imagePath = MutableLiveData<String>()
    val imagePath: LiveData<String> get() = _imagePath

    val editTextTitle = MutableLiveData<String>()

    val editTextDescription = MutableLiveData<String>()

    val editTextRecipe = MutableLiveData<String>()

    private var _listIngredients = MutableLiveData<ArrayList<String>>()
    val listIngredients: LiveData<ArrayList<String>> get() = _listIngredients

    fun insertCocktail() {
        viewModelScope.launch {
            val cocktail = _listIngredients.value?.let {
                Log.i("abc", editTextDescription.value.toString())


                _imagePath.value?.let { it1 ->
                    CocktailLocalDataSource(
                        0,
                        editTextTitle.value.toString(),
                        it1,
                        editTextDescription.value.toString(),
                        it,
                        editTextRecipe.value.toString())
                }
            }
            cocktail?.let { repository.insert(it) }
        }
    }

    fun addIngredient(ingredient: String) {
        val oldListIngredient = _listIngredients.value
        val newListIngredient = ArrayList<String>()

        if (oldListIngredient != null) {

            for(ingr in oldListIngredient) {
                newListIngredient.add(ingr)
            }

        }
        newListIngredient.add(ingredient)

        Log.i("abc1", newListIngredient.toString())

        _listIngredients.value = newListIngredient
    }

    fun addImageCocktailUri(path: String) {

        _imagePath.value = path
    }




}