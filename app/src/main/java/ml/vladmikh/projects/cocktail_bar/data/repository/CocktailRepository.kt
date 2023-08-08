package ml.vladmikh.projects.cocktail_bar.data.repository

import ml.vladmikh.projects.cocktail_bar.data.local.dao.CocktailDao
import ml.vladmikh.projects.cocktail_bar.data.local.entities.CocktailLocalDataSource
import javax.inject.Inject

class CocktailRepository @Inject constructor(private val cocktailDao: CocktailDao) {

    suspend fun insert(cocktailLocalDataSource: CocktailLocalDataSource) = cocktailDao.insert(cocktailLocalDataSource)

    suspend fun update(cocktailLocalDataSource: CocktailLocalDataSource) = cocktailDao.update(cocktailLocalDataSource)

    suspend fun delete(cocktailLocalDataSource: CocktailLocalDataSource) = cocktailDao.delete(cocktailLocalDataSource)

    fun getAllCocktails() = cocktailDao.getAllCocktails()

    fun getCocktail(id: Int) = cocktailDao.getCocktail(id)
}