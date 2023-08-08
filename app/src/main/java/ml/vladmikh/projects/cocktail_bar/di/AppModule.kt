package ml.vladmikh.projects.cocktail_bar.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ml.vladmikh.projects.cocktail_bar.data.local.CocktailBarDatabase
import ml.vladmikh.projects.cocktail_bar.data.local.dao.CocktailDao
import ml.vladmikh.projects.cocktail_bar.data.repository.CocktailRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesCocktailDao(database: CocktailBarDatabase): CocktailDao {
        return database.cocktailDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CocktailBarDatabase {
        return Room.databaseBuilder(
            context,
            CocktailBarDatabase::class.java,
            "cocktail_bar_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    @Provides
    @Singleton
    fun providesCocktailRepository(cocktailDao: CocktailDao): CocktailRepository =
        CocktailRepository(cocktailDao)
}