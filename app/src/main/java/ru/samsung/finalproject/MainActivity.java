package ru.samsung.finalproject;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    private static final int PERMISSION_STORAGE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!PermissionUtils.hasPermissions(MainActivity.this)){
            PermissionUtils.requestPermissions(MainActivity.this, PERMISSION_STORAGE);
        }
        ArrayList<BookItem> books = new ArrayList<>();
        listView = findViewById(R.id.list);
        //в onCreate добавить if(intent)=>добавить элемент(после интента должно открываться приложение мейн активити)(интент фильтр)
        books.add(new BookItem("Code", false, "/Download/Test123.txt"));//для эмулятора
        books.add(new BookItem("Consp", false, "/Конспект.txt"));//для мобильного
        books.add(new BookItem("Конспект", false, "/Regex.cpp.txt"));//для мобильного
        BaseAdapter adapter = new BookAdapter(this, books);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        Intent intent = getIntent();
        try {
            String name = String.valueOf(intent.getClipData().getItemAt(1).getUri());
            Log.d("INTENTAG", name);
        }catch(Exception e){
            Log.d("INTENTAG", e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reader_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.list:
                Intent intent = new Intent(MainActivity.this, QuoteActivity.class );
                startActivity(intent);
        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        BookItem info = (BookItem) adapterView.getAdapter().getItem(i);
        String intent_file_path = info.getFilePath();
        Intent intent = new Intent(MainActivity.this, ReaderActivity.class);
        intent.putExtra("INTENT_FILE_PATH", intent_file_path);
        startActivity(intent);
    }
}