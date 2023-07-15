package com.kyawzinlinn.moviesapp.data.local.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("search_history")
data class MovieSearchHistory(
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo("id")
    val id: Int,

    @ColumnInfo("name")
    val name: String
)
