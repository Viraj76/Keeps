package com.example.notes.Repository

import androidx.lifecycle.LiveData
import com.example.notes.Dao.NotesDao
import com.example.notes.Model.Notes

class NotesRepository(private val dao:NotesDao) {

      fun getAllNotes(currentUserId: String?): LiveData<List<Notes>> {
        return dao.getNotes(currentUserId)
    }

     fun getHighNotes(currentUserId: String?): LiveData<List<Notes>> = dao.getHighNotes(currentUserId)
     fun  getMediumNotes(currentUserId: String?): LiveData<List<Notes>> = dao.getMediumNotes(currentUserId)
     fun getLowNotes(currentUserId: String?): LiveData<List<Notes>> = dao.getLowNotes(currentUserId)

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