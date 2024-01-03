package ml.vladmikh.projects.cocktail_bar.data.local.entities

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cocktail_table")
data class CocktailLocalDataSource (

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    @ColumnInfo(name = "image_url")val imagePath: String,
    val description: String,
    val ingredients: List<String>,
    val recipe: String

) {

}

