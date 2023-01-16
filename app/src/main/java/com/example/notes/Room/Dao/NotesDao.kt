package com.example.notes.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notes.Model.Notes

@Dao
interface NotesDao {

    @Query("SELECT * FROM Notes")
   suspend fun getNotes():LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertNotes(notes :Notes)

    @Query("DELETE FROM Notes WHERE id=:id")
   suspend fun deleteNotes(id:Int)

    @Update
   suspend fun updateNotes(notes:Notes)

}