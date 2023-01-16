package com.example.notes.Repository

import android.database.sqlite.SQLiteStatement
import androidx.lifecycle.LiveData
import com.example.notes.Dao.NotesDao
import com.example.notes.Model.Notes

class NotesRepository(private val dao:NotesDao) {

    suspend fun getAllNotes(): LiveData<List<Notes>> {
        return dao.getNotes()
    }

    suspend fun insertNotes(notes:Notes){
        dao.insertNotes(notes)
    }

    suspend fun deleteNotes(id:Int){
        dao.deleteNotes(id)
    }

    suspend fun updateNotes(notes:Notes){
        dao.updateNotes(notes)
    }

}