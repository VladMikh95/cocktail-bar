package ml.vladmikh.projects.cocktail_bar.ui.add_cocktail

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

    val editTextTitle = MutableLiveData<String>()

    private var _listIngredients = MutableLiveData<ArrayList<String>>()
    val listIngredients: LiveData<ArrayList<String>> get() = _listIngredients

    fun insertCocktail(cocktail: CocktailLocalDataSource) {
        viewModelScope.launch {
            repository.insert(cocktail)
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


}