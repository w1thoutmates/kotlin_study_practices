package com.denis.practice23

import androidx.room.Embedded
import androidx.room.Relation

data class DietWithMammals(
    @Embedded val diet: Diet,
    @Relation(
        parentColumn = "dietId",
        entityColumn = "mammalDietId"
    ) val mammals: List<Mammal>
)