package com.example.final_app.dataGroup


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class GroupRepository(private val groupDao: GroupDao) {
    val readAllGroups: LiveData<List<Group>> = groupDao.readAllGroups()

    suspend fun getSubGroupsOfGroup(gName:String): List<SubGroup> {
        return groupDao.getSubGroupsOfGroup(gName)
    }

    suspend fun addGroup(group:Group){
        groupDao.addGroup(group)
    }

    suspend fun deleteGroup(name: String){
        groupDao.deleteGroup(name)
    }

    suspend fun updateGroup(oldName:String, newName:String){
        groupDao.updateGroup(oldName,newName)
    }

    suspend fun addSubGroup(subGroup: SubGroup){
        groupDao.addSubGroup(subGroup)
    }

    suspend fun deleteSubGroup(name: String){
        groupDao.deleteSubGroup(name)
    }

    suspend fun updateSubGroup(oldName:String, newName:String){
        groupDao.updateSubGroup(oldName,newName)
    }



}