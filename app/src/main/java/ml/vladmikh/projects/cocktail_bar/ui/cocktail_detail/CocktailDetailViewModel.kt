package ml.vladmikh.projects.cocktail_bar.ui.cocktail_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ml.vladmikh.projects.cocktail_bar.data.local.entities.CocktailLocalDataSource
import ml.vladmikh.projects.cocktail_bar.data.repository.CocktailRepository
import ml.vladmikh.projects.cocktail_bar.ui.my_cocktails.CocktailsAdapter
import javax.inject.Inject

@HiltViewModel
class CocktailDetailViewModel @Inject constructor(
    private val repository: CocktailRepository
): ViewModel() {

    private val _cocktail = MutableLiveData<CocktailLocalDataSource>()
    lateinit var cocktail: LiveData<CocktailLocalDataSource>

    fun getCocktail (id: Int) {
        cocktail = repository.getCocktail(id).asLiveData()
    }

    fun deleteCocktail(cocktail: CocktailLocalDataSource) {
        viewModelScope.launch {
            repository.delete(cocktail)
        }
    }




}