package com.example.notes.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.notes.Database.NotesDatabase
import com.example.notes.Model.Notes
import com.example.notes.Repository.NotesRepository

class NotesViewModel(application: Application) :AndroidViewModel(application){

    private val repository:NotesRepository
    init{
        val dao=NotesDatabase.getDatabaseInstance(application).myNotesDao()
        repository=NotesRepository(dao)
    }

     suspend fun addNotes(notes: Notes){
        repository.insertNotes(notes)
    }

     suspend fun getNotes(): LiveData<List<Notes>> = repository.getAllNotes()
    suspend fun getHighNotes(): LiveData<List<Notes>> = repository.getHighNotes()
    suspend fun  getMediumNotes(): LiveData<List<Notes>> = repository.getMediumNotes()
    suspend fun getLowNotes(): LiveData<List<Notes>> = repository.getLowNotes()


     suspend fun deleteNotes(id:Int){
        repository.deleteNotes(id)
    }
     fun updateNotes(notes:Notes){
        repository.updateNotes(notes)
    }


}