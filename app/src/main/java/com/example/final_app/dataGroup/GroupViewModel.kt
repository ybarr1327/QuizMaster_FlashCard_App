package com.example.final_app.dataGroup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GroupViewModel(application: Application):AndroidViewModel(application) {
    val readAllGroups: LiveData<List<Group>> // this stores the live data for the groups
    val returnedSubGroups = MutableLiveData<List<SubGroup>>() // this is the live data for the subgroups
    val returnedFlashCards = MutableLiveData<List<FlashCard>>() //this is the live data for the returned flashcards

    private val repository:GroupRepository // creates an instance of the Repository
    init {
        val groupDao = GroupDatabase.getDatabase(application).groupDao()
        repository = GroupRepository(groupDao)
        readAllGroups = repository.readAllGroups
    }


    //data access functions, these basically update the live data when called
    fun getSubGroupsOfGroup(gName:String){
        viewModelScope.launch {
            returnedSubGroups.postValue(repository.getSubGroupsOfGroup(gName))
        }
    }

    fun getFlashcards(parent: String) {
        viewModelScope.launch {
            returnedFlashCards.postValue(repository.getFlashcards(parent))
        }
    }

    // group functions

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

    //subgroup functions

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

    //flashcard functions

    fun addFlashcard(flashCard: FlashCard){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFlashcard(flashCard)
        }
    }

    fun editFlashcard(curFront:String, newFront:String, newBack:String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.editFlashcard(curFront, newFront, newBack)
        }
    }

    fun deleteFlashcard(front: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFlashcard(front)
        }
    }


}