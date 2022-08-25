package com.example.app_note;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ImageView;

import com.example.app_note.Adapter.NoteAdapter;
import com.example.app_note.Database.NoteDAO;
import com.example.app_note.Database.NoteDatabase;
import com.example.app_note.Model.Note;

import java.util.ArrayList;
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


        //nListNote = NoteDatabase.getInstance(this).noteDAO().getListNote();
        rcyVNote.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rcyVNote.addItemDecoration(dividerItemDecoration);
        noteAdapter = new NoteAdapter(nListNote,this);
        rcyVNote.setAdapter(noteAdapter);

        loadDataList();

//        rcyVNote.setAdapter(new NoteAdapter(nListNote, MainActivity.this));
//        noteAdapter = new NoteAdapter(nListNote,this);
//        rcyVNote.setAdapter(noteAdapter);
//        noteAdapter.setData(nListNote);
//        noteAdapter.notifyDataSetChanged();

        imageView.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        });
//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
//
//                int year = i;
//                int month = i1+1;
//                int date = i2;
//                Toast.makeText(MainActivity.this, year+"/"+month+"/"+date, Toast.LENGTH_SHORT).show();
//            }
//        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                int year = i;
                int month = i1+1;
                int date = i2;
                nListNote = new ArrayList<>();
                nListNote = NoteDatabase.getInstance(MainActivity.this).noteDAO().calenderNote(date);
                noteAdapter.setData(nListNote);
            }
        });
    }



    private void loadDataList(){
        NoteDatabase db = NoteDatabase.getInstance(this.getApplicationContext());
        nListNote = db.noteDAO().getListNote();
        noteAdapter.setData(nListNote);
        noteAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 100){
            loadDataList();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        nListNote.clear();
        nListNote.addAll(NoteDatabase.getInstance(this).noteDAO().getListNote());
    }
}