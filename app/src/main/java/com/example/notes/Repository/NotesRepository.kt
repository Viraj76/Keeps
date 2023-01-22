package com.example.notes.Repository

import androidx.lifecycle.LiveData
import com.example.notes.Dao.NotesDao
import com.example.notes.Model.Notes

class NotesRepository(private val dao:NotesDao) {

      fun getAllNotes(): LiveData<List<Notes>> {
        return dao.getNotes()
    }

     fun getHighNotes(): LiveData<List<Notes>> = dao.getHighNotes()
     fun  getMediumNotes(): LiveData<List<Notes>> = dao.getMediumNotes()
     fun getLowNotes(): LiveData<List<Notes>> = dao.getLowNotes()

    suspend fun insertNotes(notes:Notes){
        dao.insertNotes(notes)
    }

    suspend fun deleteNotes(id:Int){
        dao.deleteNotes(id)
    }

     fun updateNotes(notes:Notes){
        dao.updateNotes(notes)
    }

}