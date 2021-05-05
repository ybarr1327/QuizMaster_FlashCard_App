package com.example.final_app.dataGroup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GroupViewModel(application: Application):AndroidViewModel(application) {
    val readAllGroups: LiveData<List<Group>>
    val returnedSubGroups = MutableLiveData<List<SubGroup>>()

    private val repository:GroupRepository
    init {
        val groupDao = GroupDatabase.getDatabase(application).groupDao()
        repository = GroupRepository(groupDao)
        readAllGroups = repository.readAllGroups
    }

    fun getSubGroupsOfGroup(gName:String){
        viewModelScope.launch {
            returnedSubGroups.postValue(repository.getSubGroupsOfGroup(gName))
        }
    }

    fun addGroup(group: Group){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addGroup(group)
        }
    }

    fun deleteGroup(name: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteGroup(name)
        }
    }

    fun updateGroup(oldName:String, newName:String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateGroup(oldName, newName)
        }
    }

    fun addSubGroup(subGroup: SubGroup){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSubGroup(subGroup)
        }
    }

    fun deleteSubGroup(name: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSubGroup(name)
        }
    }

    fun updateSubGroup(oldName:String, newName:String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSubGroup(oldName, newName)
        }
    }


}