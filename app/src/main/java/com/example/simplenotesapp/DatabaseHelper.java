package com.example.simplenotesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "notes.db";
    public static final String TABLE_NAME = "notes";
    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);

    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE notes(id INTEGER PRIMARY KEY AUTOINCREMENT,note TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS notes");
        onCreate(db);
    }

    public boolean insertNote(String note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("note",note);
        long result =db.insert(TABLE_NAME,null,values);
        return result!=-1;
    }

    public Cursor getAllNotes(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM notes",null);
    }

   public void deleteNote(String note){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,"note=?",new String[]{note});
   }

}
