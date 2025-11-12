package com.denis.practice23

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Mammal(
    @PrimaryKey(autoGenerate = true) val mammalId: Int = 0,
    @ColumnInfo val mammalName: String,
    @ColumnInfo val species: String,
    @ColumnInfo val mammalDietId: Int
)