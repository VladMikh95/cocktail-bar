package ml.vladmikh.projects.cocktail_bar.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ml.vladmikh.projects.cocktail_bar.data.local.dao.CocktailDao
import ml.vladmikh.projects.cocktail_bar.data.local.entities.CocktailLocalDataSource

@Database(entities = [CocktailLocalDataSource::class], version = 1, exportSchema = false)
@TypeConverters(ArrayListConverter::class)
abstract class CocktailBarDatabase: RoomDatabase() {

    abstract fun cocktailDao(): CocktailDao
}