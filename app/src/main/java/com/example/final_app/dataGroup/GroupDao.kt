package com.example.final_app.dataGroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*


@Dao
interface GroupDao {
    @Query("SELECT * FROM group_table ORDER BY groupName ASC")
    fun readAllGroups(): LiveData<List<Group>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGroup(group:Group)

    @Query("DELETE FROM group_table where groupName=:name")
    suspend fun deleteGroup(name: String)

    @Query("update group_table set groupName=:newName where groupName=:oldName")
    suspend fun updateGroup(oldName: String, newName:String)

    //-------------------------------------------------------------
    @Query("select * from sub_group_table where sgParentGroupName = :gName")
    suspend fun getSubGroupsOfGroup(gName:String):List<SubGroup>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSubGroup(subGroup: SubGroup)

    @Query("DELETE FROM sub_group_table where subGroupName=:name")
    suspend fun deleteSubGroup(name: String)

    @Query("update sub_group_table set subGroupName=:newName where subGroupName=:oldName")
    suspend fun updateSubGroup(oldName: String, newName:String)

    //--------------------------------------------------------------

    @Query("select * from flashcard_table where FCParentSubGroup = :parent")
    suspend fun getFlashcards(parent: String):List<FlashCard>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFlashcard(flashCard: FlashCard)

    @Query("update flashcard_table set FC_Front = :newFront, FC_back = :newBack where FC_Front = :curFront")
    suspend fun editFlashcard(curFront:String, newFront:String, newBack:String)

    @Query("delete from flashcard_table where FC_Front = :front")
    suspend fun deleteFlashcard(front:String)

}