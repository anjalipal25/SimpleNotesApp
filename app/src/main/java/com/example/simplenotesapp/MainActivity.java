package com.example.simplenotesapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etnotes;
    Button btnsave;
    ListView listview;
    DatabaseHelper db;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etnotes = findViewById(R.id.etNotes);
        btnsave = findViewById(R.id.btnsave);
        listview = findViewById(R.id.listview);
        db = new DatabaseHelper(this);

        btnsave.setOnClickListener(v -> {
            String note = etnotes.getText().toString().trim();//trim the extra space
            if(note.isEmpty()){
                Toast.makeText(this, "Please enter a note", Toast.LENGTH_SHORT).show();
                return;// Exit the method if the note is empty
            }
            if (db.insertNote(note)) {
                Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
                etnotes.setText("");
                loadNotes();
            } else {
                Toast.makeText(this, "Failed to save note", Toast.LENGTH_SHORT).show();
            }
        });
        loadNotes();

        listview.setOnItemLongClickListener((parent, view, position, id) -> {
            String selectedNote = list.get(position);
            db.deleteNote(selectedNote);
            Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            loadNotes();
            return true;
        });
    }

        private void loadNotes ()
        {
            Cursor cursor = db.getAllNotes();
            list = new ArrayList<>();
            while (cursor.moveToNext()) {
                list.add(cursor.getString(1));
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
            listview.setAdapter(adapter);
        }


    }



