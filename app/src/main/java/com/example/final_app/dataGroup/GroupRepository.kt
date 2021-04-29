package com.example.final_app.dataGroup

import android.security.keystore.StrongBoxUnavailableException
import androidx.lifecycle.LiveData

class GroupRepository(private val groupDao: GroupDao) {
    val readAllData: LiveData<List<Group>> = groupDao.readAllData()

    suspend fun addGroup(group:Group){
        groupDao.addGroup(group)
    }

    suspend fun deleteAllGroups(){
        groupDao.deleteAllGroups()
    }

    suspend fun deleteGroup(name: String){
        groupDao.deleteGroup(name)
    }

    suspend fun updateGroup(oldName:String, newName:String){
        groupDao.updateGroup(oldName,newName)
    }



}