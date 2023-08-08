package ml.vladmikh.projects.cocktail_bar.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson

class ArrayListConverter {

    @TypeConverter
    fun listToJson(value: List<String>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
}