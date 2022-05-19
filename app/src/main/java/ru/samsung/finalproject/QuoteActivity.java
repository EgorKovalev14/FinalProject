package ru.samsung.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class QuoteActivity extends AppCompatActivity {
    ListView listView;
    DBQuotes dbQuotes;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quote_activity);
        if(getSupportActionBar()!=null)
            this.getSupportActionBar().hide();
        dbQuotes = new DBQuotes(this);
        listView = findViewById(R.id.quote_list);
        ArrayList<QuoteItem> quotes = new ArrayList<>();
        for(int i = 0; i<dbQuotes.selectAll().size(); i++){
            quotes.add(new QuoteItem(dbQuotes.selectAll().get(i).toString()));
        }
        BaseAdapter adapter = new QuoteAdapter(this, quotes);
        listView.setAdapter(adapter);
    }
}
