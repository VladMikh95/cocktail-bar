package ml.vladmikh.projects.cocktail_bar.ui.add_cocktail

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

    private var _cocktail = MutableLiveData<CocktailLocalDataSource>()
    val coctail: LiveData<CocktailLocalDataSource> get() = _cocktail


    private var _ingredients = MutableLiveData<ArrayList<String>>()
    val ingredients: LiveData<ArrayList<String>> get() = _ingredients

    fun addIngredient(ingredient: String) {
        _ingredients.value?.add(ingredient)
    }
    fun insertCocktail(cocktail: CocktailLocalDataSource) {
        viewModelScope.launch {
            repository.insert(cocktail)
        }
    }


}