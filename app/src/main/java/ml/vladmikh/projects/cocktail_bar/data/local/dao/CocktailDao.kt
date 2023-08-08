package ml.vladmikh.projects.cocktail_bar.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ml.vladmikh.projects.cocktail_bar.data.local.entities.CocktailLocalDataSource

@Dao
interface CocktailDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cocktailLocalDataSourse: CocktailLocalDataSource)

    @Update
    suspend fun update(cocktailLocalDataSourse: CocktailLocalDataSource)

    @Delete
    suspend fun delete(cocktailLocalDataSourse: CocktailLocalDataSource)

    @Query("SELECT * from cocktail_table")
    fun getAllCocktails(): Flow<List<CocktailLocalDataSource>>

    @Query("SELECT * from cocktail_table WHERE id = :idCocktail")
    fun getCocktail(idCocktail: Int): Flow<CocktailLocalDataSource>
}