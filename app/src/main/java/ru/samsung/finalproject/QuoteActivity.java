package ru.samsung.finalproject;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class QuoteActivity extends AppCompatActivity {
    ListView listView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quote_activity);
        ArrayList<QuoteItem> quotes = new ArrayList<>();
        listView = findViewById(R.id.quote_list);
    }
}
