package com.example.books;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Irrigation Guide");
        mRecyclerView =(RecyclerView) findViewById(R.id.recyclerview_books);
        new FirebaseDatabaseHelper().readSensors(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Sensor> sensors, List<String> keys) {
                new RecyclerView_Config().setConfig(mRecyclerView, MainActivity.this,sensors,keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }
}
