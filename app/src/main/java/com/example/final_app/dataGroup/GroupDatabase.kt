package com.example.final_app.dataGroup

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
 //this is the definition of the database
@Database(entities = [Group::class, SubGroup::class, FlashCard::class], version = 1, exportSchema = false)
abstract class GroupDatabase:RoomDatabase() {
    abstract fun groupDao():GroupDao // this is the data access object

    companion object{
        @Volatile
        private var INSTANCE: GroupDatabase?= null // create an instance of the database

        fun getDatabase(context: Context):GroupDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){ // build the database
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