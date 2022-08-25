package com.example.app_note.Adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_note.MainActivity2;
import com.example.app_note.Model.Note;
import com.example.app_note.R;

import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> listNote;

    private Context context;

    public NoteAdapter(List<Note> listNote, Context context) {
        this.listNote = listNote;
        this.context = context;
    }

    public void setData(List<Note> list){
        this.listNote = list;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent,false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = listNote.get(position);

        holder.tvName.setText("Tên Note: "+note.getNameNote());
        holder.tvDate.setText("Ngày giờ: "+note.getNgayGio());
        holder.tvNote.setText("Ghi chú: "+note.getGhichuNote());
        holder.tvPickColor.setText("Pick Note: "+note.getPickMau());
//        holder.cardPickColor.setBackgroundColor(defaultColor);
    }

    @Override
    public int getItemCount() {
        return this.listNote.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvNote;
        private TextView tvDate;
        private TextView tvPickColor;
        private TextView tvPC;
        private int defaultColor;
        CardView cardPickColor;


        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvNote = (TextView) itemView.findViewById(R.id.tvNote);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvPickColor = (TextView) itemView.findViewById(R.id.tvPickColor);
            cardPickColor = itemView.findViewById(R.id.cardPickColor);
            tvPC = itemView.findViewById(R.id.tvPC);

            defaultColor = ContextCompat.getColor(context, com.google.android.material.R.color.design_default_color_primary);

            tvPC.setOnClickListener(view -> {
                AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(context, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {

                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        defaultColor = color;
                        cardPickColor.setBackgroundColor(defaultColor);
                    }
                });
                ambilWarnaDialog.show();
            });

          //  cardPickColor= itemView.findViewById(R.id.cardPickColor);


        }
    }
}
