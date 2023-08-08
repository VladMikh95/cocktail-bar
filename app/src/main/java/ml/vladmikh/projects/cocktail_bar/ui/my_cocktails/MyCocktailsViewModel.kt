package ml.vladmikh.projects.cocktail_bar.ui.my_cocktails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ml.vladmikh.projects.cocktail_bar.data.local.entities.CocktailLocalDataSource
import ml.vladmikh.projects.cocktail_bar.data.repository.CocktailRepository
import javax.inject.Inject

@HiltViewModel
class MyCocktailsViewModel @Inject constructor(
    private val repository: CocktailRepository
): ViewModel() {

    val listCocktails: LiveData<List<CocktailLocalDataSource>> = repository.getAllCocktails().asLiveData()

}