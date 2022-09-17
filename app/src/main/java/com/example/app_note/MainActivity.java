package com.example.app_note;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.example.app_note.Adapter.NoteAdapter;
import com.example.app_note.Database.NoteDAO;
import com.example.app_note.Database.NoteDatabase;
import com.example.app_note.Model.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private CalendarView calendarView;
    private RecyclerView rcyVNote;
    private NoteDAO noteDAO;
    private List<Note> nListNote;
    NoteAdapter noteAdapter;

    private Button btn_Buy;

    BillingClient billingClient;
    PurchasesUpdatedListener purchasesUpdatedListener;
    List<ProductDetails> productDetailsList;
    ProductDetails productDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        rcyVNote = (RecyclerView) findViewById(R.id.rcyV_Note);
        btn_Buy = findViewById(R.id.btnMua);

        btn_Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ThanhToanActivity.class);
                startActivity(intent);
            }
        });


        purchasesUpdatedListener = new PurchasesUpdatedListener() {
            @Override
            public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {

            }
        };


        //nListNote = NoteDatabase.getInstance(this).noteDAO().getListNote();
        rcyVNote.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rcyVNote.addItemDecoration(dividerItemDecoration);
        noteAdapter = new NoteAdapter(nListNote,this);
        rcyVNote.setAdapter(noteAdapter);

        loadDataList();


        imageView.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        });


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                String date = "";
                Calendar calendar=Calendar.getInstance();
                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);
                SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("dd/MM/yyyy");
                date=simpleDateFormat1.format(calendar.getTime());
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