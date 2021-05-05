package com.example.final_app.dataGroup

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Group::class, SubGroup::class], version = 1, exportSchema = false)
abstract class GroupDatabase:RoomDatabase() {
    abstract fun groupDao():GroupDao

    companion object{
        @Volatile
        private var INSTANCE: GroupDatabase?= null

        fun getDatabase(context: Context):GroupDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GroupDatabase::class.java,
                    "group_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}