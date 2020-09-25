package com.rujirakongsomran.roomdatabasenoteapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

// Define table
@Entity(tableName = "table_note")
public class MainData implements Serializable {

    // Create id column
    @PrimaryKey(autoGenerate = true)
    private int Id;

    // Create text column
    @ColumnInfo(name = "text")
    private String text;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
