package com.example.app_note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.app_note.Adapter.NoteAdapter;
import com.example.app_note.Database.NoteDatabase;
import com.example.app_note.Model.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

        edPickMau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnInsert.setOnClickListener(view -> {
            addNote();
        });

        edNgayGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,day);
                        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                calendar.set(Calendar.MINUTE,minute);

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");
                                edNgayGio.setText(simpleDateFormat.format(calendar.getTime()));
                            }
                        };
                        new TimePickerDialog(MainActivity2.this,onTimeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
                    }
                };
                new DatePickerDialog(MainActivity2.this,onDateSetListener,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
    }

    private void showDateTimeDialog(EditText date_time_in) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");
                        date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };
                new TimePickerDialog(MainActivity2.this,onTimeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        };
        new DatePickerDialog(MainActivity2.this,onDateSetListener,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
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
        Toast.makeText(this, " Add Note Successfully ", Toast.LENGTH_SHORT).show();

        edName.setText("");
        edNgayGio.setText("");
        edGhiChu.setText("");
        edPickMau.setText("");
    }
}