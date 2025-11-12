package com.denis.practice23

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(Mammal::class), (Diet::class)], version = 3)
abstract class DataMammal: RoomDatabase(){
    abstract fun mammalDao(): MammalDao

    abstract fun dietDao(): DietDao

    abstract fun dietWithMammalsDao(): DietWithMammalsDao

    companion object {
        private var INSTANCE: DataMammal? = null
        fun getInstance(context: Context): DataMammal {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DataMammal::class.java,
                        "Mammal_database"

                    ).fallbackToDestructiveMigration(true).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}