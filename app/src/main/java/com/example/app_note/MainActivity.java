package com.example.app_note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;

import com.example.app_note.Adapter.NoteAdapter;
import com.example.app_note.Database.NoteDAO;
import com.example.app_note.Database.NoteDatabase;
import com.example.app_note.Database.NoteDatabase_Impl;
import com.example.app_note.Model.Note;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private CalendarView calendarView;
    private RecyclerView rcyVNote;
    private NoteDAO noteDAO;
    private List<Note> nListNote;
    NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        rcyVNote = (RecyclerView) findViewById(R.id.rcyV_Note);
        nListNote = NoteDatabase.getInstance(this).noteDAO().getListNote();
        rcyVNote.setLayoutManager(new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false));
        rcyVNote.setAdapter(new NoteAdapter(nListNote, MainActivity.this));

        noteAdapter = new NoteAdapter(nListNote,this);

        rcyVNote.setAdapter(noteAdapter);
        noteAdapter.setData(nListNote);
        noteAdapter.notifyDataSetChanged();

        imageView.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        });

    }
}