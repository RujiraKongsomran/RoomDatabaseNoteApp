package com.rujirakongsomran.roomdatabasenoteapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.rujirakongsomran.roomdatabasenoteapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    List<MainData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Initialize database
        database = RoomDB.getInstance(this);

        // Store database value in data list
        dataList = database.mainDao().getAll();

        // Initialize linear layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        // Set layout manager
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        // Initialize adapter
        adapter = new MainAdapter(dataList, MainActivity.this);
        binding.recyclerView.setAdapter(adapter);

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get string from edit text
                String sText = binding.etMessage.getText().toString().trim();

                if(!sText.equals("")){
                    // When text is not empty
                    // Initialize main data
                    MainData data = new MainData();
                    // Set text on main data
                    data.setText(sText);
                    // Insert text in database
                    database.mainDao().insert(data);
                    // Clear edit text
                    binding.etMessage.setText("");
                    // Notify when data is inserted
                    dataList.clear();
                    dataList.addAll(database.mainDao().getAll());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        binding.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delete all data from database
                database.mainDao().reset(dataList);
                // Notify when all data deleted
                dataList.clear();
                dataList.addAll(database.mainDao().getAll());
                adapter.notifyDataSetChanged();
            }
        });

    }
}