package com.example.app_note.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "note")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nameNote;
    private String ngayGio;
    private String ghichuNote;
    private String pickMau;

    public Note() {
    }

    public Note( String nameNote, String ngayGio, String ghichuNote, String pickMau) {

        this.nameNote = nameNote;
        this.ngayGio = ngayGio;
        this.ghichuNote = ghichuNote;
        this.pickMau = pickMau;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameNote() {
        return nameNote;
    }

    public void setNameNote(String nameNote) {
        this.nameNote = nameNote;
    }

    public String getNgayGio() {
        return ngayGio;
    }

    public void setNgayGio(String ngayGio) {
        this.ngayGio = ngayGio;
    }

    public String getGhichuNote() {
        return ghichuNote;
    }

    public void setGhichuNote(String ghichuNote) {
        this.ghichuNote = ghichuNote;
    }

    public String getPickMau() {
        return pickMau;
    }

    public void setPickMau(String pickMau) {
        this.pickMau = pickMau;
    }
}