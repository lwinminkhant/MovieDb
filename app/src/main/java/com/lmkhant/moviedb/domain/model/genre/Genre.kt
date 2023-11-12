package com.lmkhant.moviedb.domain.model.genre


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.lmkhant.moviedb.data.typeconverter.GenreListConverter
import kotlinx.serialization.Serializable

data class GenreList(
    @SerializedName("genres")
    val genres: List<Genre>
)
@Entity(tableName = "genres")
@TypeConverters(GenreListConverter::class)
@Serializable
data class Genre(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)