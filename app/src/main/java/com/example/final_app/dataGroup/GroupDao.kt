package com.example.final_app.dataGroup

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface GroupDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGroup(group:Group)

    @Query("SELECT * FROM group_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Group>>

    @Query("DELETE FROM group_table")
    suspend fun deleteAllGroups()


}