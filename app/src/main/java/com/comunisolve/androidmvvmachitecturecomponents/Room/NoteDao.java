package com.comunisolve.androidmvvmachitecturecomponents.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//Dao contains all the methods of operations related to our database
@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM name_table")
    void deleteAllNotes();

    @Query("SELECT * FROM name_table ORDER BY priority DESC")
    LiveData<List<Note>> getAllNotes(); // observable, if feel any changes then we will update our UI.




}
