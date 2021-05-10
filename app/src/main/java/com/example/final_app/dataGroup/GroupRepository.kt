package com.example.final_app.dataGroup


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class GroupRepository(private val groupDao: GroupDao) {
    //data read access

    val readAllGroups: LiveData<List<Group>> = groupDao.readAllGroups()

    suspend fun getSubGroupsOfGroup(gName:String): List<SubGroup> {
        return groupDao.getSubGroupsOfGroup(gName)
    }

    suspend fun getFlashcards(parent: String) : List<FlashCard> {
        return groupDao.getFlashcards(parent)
    }

    //group functions

    suspend fun addGroup(group:Group){
        groupDao.addGroup(group)
    }

    suspend fun deleteGroup(name: String){
        groupDao.deleteGroup(name)
    }

    suspend fun updateGroup(oldName:String, newName:String){
        groupDao.updateGroup(oldName,newName)
    }

    //subgroup functions

    suspend fun addSubGroup(subGroup: SubGroup){
        groupDao.addSubGroup(subGroup)
    }

    suspend fun deleteSubGroup(name: String){
        groupDao.deleteSubGroup(name)
    }

    suspend fun updateSubGroup(oldName:String, newName:String){
        groupDao.updateSubGroup(oldName,newName)
    }

    //flashcard functions

    suspend fun addFlashcard(flashCard: FlashCard){
        groupDao.addFlashcard(flashCard)
    }

    suspend fun editFlashcard(curFront:String, newFront:String, newBack:String)
    {
        groupDao.editFlashcard(curFront, newFront, newBack)
    }

    suspend fun deleteFlashcard(front: String){
        groupDao.deleteFlashcard(front)
    }

}