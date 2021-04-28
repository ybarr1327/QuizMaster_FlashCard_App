package com.example.final_app.dataGroup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GroupViewModel(application: Application):AndroidViewModel(application) {
    val readAllData: LiveData<List<Group>>
    private val repository:GroupRepository


    init {
        val groupDao = GroupDatabase.getDatabase(application).groupDao()
        repository = GroupRepository(groupDao)
        readAllData = repository.readAllData
    }



    fun addGroup(group: Group){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addGroup(group)
        }
    }

    fun deleteAllGroups(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllGroups()
        }
    }

    fun deleteGroup(name: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteGroup(name)
        }
    }
}