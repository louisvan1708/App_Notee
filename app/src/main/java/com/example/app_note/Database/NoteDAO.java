package com.example.app_note.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.app_note.Model.Note;

import java.util.List;

@Dao
public interface NoteDAO {

    @Insert
    void insertNote(Note note);

    @Query("SELECT * FROM note")
    List<Note> getListNote();

    @Query("SELECT * FROM note WHERE ngayGio LIKE '%' || :time || '%'")
    List<Note> calenderNote(int time);



}
