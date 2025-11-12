package com.denis.practice23

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Diet(
    @PrimaryKey(autoGenerate = true) val dietId: Int = 0,
    @ColumnInfo val dietType: String,
    @ColumnInfo val description: String
)