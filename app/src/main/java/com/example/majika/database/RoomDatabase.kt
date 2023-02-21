package com.example.majika.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class RoomDataBase : RoomDatabase() {
    abstract fun keranjangDAO(): KeranjangDAO

    companion object {
        @Volatile
        private var INSTANCE: RoomDataBase? = null

        fun getDatabase(context: Context): RoomDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDataBase::class.java,
                    "room_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}