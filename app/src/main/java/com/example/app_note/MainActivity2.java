package com.example.app_note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_note.Adapter.NoteAdapter;
import com.example.app_note.Database.NoteDatabase;
import com.example.app_note.Model.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private EditText edName;
    private EditText edNgayGio;
    private EditText edGhiChu;
    private EditText edPickMau;
    private Button btnInsert;

    private NoteAdapter noteAdapter;
    private List<Note> mListNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        edName = (EditText) findViewById(R.id.edName);
        edNgayGio = (EditText) findViewById(R.id.edNgayGio);
        edGhiChu = (EditText) findViewById(R.id.edGhiChu);
        edPickMau = (EditText) findViewById(R.id.edPickMau);
        btnInsert = (Button) findViewById(R.id.btnInsert);

        noteAdapter = new NoteAdapter(mListNote,this);
        mListNote = new ArrayList<>();
        noteAdapter.setData(mListNote);

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        btnInsert.setOnClickListener(view -> {
            addNote();
        });


    }

    private void addNote() {
        String strNoteName = edName.getText().toString().trim();
        String strNoteNgayGio = edNgayGio.getText().toString().trim();
        String strGhiChu = edGhiChu.getText().toString().trim();
        String strPickMau = edPickMau.getText().toString().trim();

        if (TextUtils.isEmpty(strNoteName) || TextUtils.isEmpty(strNoteNgayGio) || TextUtils.isEmpty(strGhiChu) || TextUtils.isEmpty(strPickMau)){
            return;
        }

        Note note = new Note(strNoteName,strNoteNgayGio,strGhiChu,strPickMau);
        NoteDatabase.getInstance(this).noteDAO().insertNote(note);
        Toast.makeText(this, "Add Note Successfully"+strNoteName, Toast.LENGTH_SHORT).show();

        edName.setText("");
        edNgayGio.setText("");
        edGhiChu.setText("");
        edPickMau.setText("");
    }
}