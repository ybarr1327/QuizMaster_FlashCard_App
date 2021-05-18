package com.example.final_app.dataGroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*


@Dao // this is the definition of the database access functions
interface GroupDao {
    @Query("SELECT * FROM group_table ORDER BY groupName ASC") // this gets all the groups ordered by name
    fun readAllGroups(): LiveData<List<Group>>

    @Insert(onConflict = OnConflictStrategy.IGNORE) // this adds a new group, on conflict the new one is ignored
    suspend fun addGroup(group:Group)

    @Query("DELETE FROM group_table where groupName=:name") // this deletes a group by the group name
    suspend fun deleteGroup(name: String)

    @Query("update group_table set groupName=:newName where groupName=:oldName") // this updates a group name
    suspend fun updateGroup(oldName: String, newName:String)

    //-------------------------------------------------------------
    @Query("select * from sub_group_table where sgParentGroupName = :gName") // this gets all subgroups of a parent group
    suspend fun getSubGroupsOfGroup(gName:String):List<SubGroup>

    @Insert(onConflict = OnConflictStrategy.IGNORE) // this adds a new subgroup, if there is a conflict the insert is ignored
    suspend fun addSubGroup(subGroup: SubGroup)

    @Query("DELETE FROM sub_group_table where subGroupName=:name") // this deletes a subgroup based on its name
    suspend fun deleteSubGroup(name: String)

    @Query("update sub_group_table set subGroupName=:newName where subGroupName=:oldName") //this updates a subgroup name
    suspend fun updateSubGroup(oldName: String, newName:String)

    //--------------------------------------------------------------

    @Query("select * from flashcard_table where FCParentSubGroup = :parent") // this gets all flashcards based on the parent subgroup
    suspend fun getFlashcards(parent: String):List<FlashCard>

    @Insert(onConflict = OnConflictStrategy.IGNORE) // this inserts a new flashcard into the subgroup set
    suspend fun addFlashcard(flashCard: FlashCard)

    @Query("update flashcard_table set FC_Front = :newFront, FC_back = :newBack where FC_Front = :curFront") //this edits the front and the back of a flashcard
    suspend fun editFlashcard(curFront:String, newFront:String, newBack:String)

    @Query("delete from flashcard_table where FC_Front = :front") // this deletes a flashcard
    suspend fun deleteFlashcard(front:String)

}