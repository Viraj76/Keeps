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


    @Query("SELECT * FROM Notes WHERE user_id = :currentUserId")
     fun getNotes(currentUserId: String?):LiveData<List<Notes>>


    @Query("SELECT * FROM Notes WHERE priority = 3 AND user_id = :currentUserId")  //here the colon means , this should be matched with the respective func parameter
     fun getHighNotes(currentUserId: String?):LiveData<List<Notes>>

    @Query("SELECT * FROM Notes WHERE priority = :priority AND user_id = :currentUserId ")
     fun getMediumNotes(currentUserId: String?, priority: Int = 2):LiveData<List<Notes>>

    @Query("SELECT * FROM Notes WHERE priority = 1 AND user_id = :currentUserId")
     fun getLowNotes(currentUserId: String?):LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes :Notes)

    @Query("DELETE FROM Notes WHERE id=:id")
    suspend fun deleteNotes(id:Int)

    @Update
    fun updateNotes(notes:Notes)

}