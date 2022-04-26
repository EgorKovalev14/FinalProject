package ru.samsung.finalproject;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
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
    DBBooks dbBooks;
    private static final int PERMISSION_STORAGE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbBooks = new DBBooks(this);
        if (!PermissionUtils.hasPermissions(MainActivity.this)) {
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

        try {

            for(int i = 0; i<dbBooks.selectAll().size(); i++){
                String b = dbBooks.selectAll().get(i).getBookFromDBName();
                Log.d("myTag", b + " FROM DATABASE");
                books.add(new BookItem(b.substring(0, b.lastIndexOf(".")), false, "/" + b));
            }
            if (getIntent().getData() != null ) {
                String name = getCursorValue();
                Log.d("myTag", name);
                books.add(new BookItem(name.substring(0, name.lastIndexOf(".")), false, "/" + name));
                dbBooks.insert(name);
            }
        }catch(Exception e){
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reader_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.quote_list:
                Intent intent = new Intent(MainActivity.this, QuoteActivity.class);
                startActivity(intent);
        }
        return true;
    }

    private String getCursorValue() {
        try {
            Cursor cursor = null;
            String result = null;
            try {
                cursor = getContentResolver().query(getIntent().getData(), new String[]{MediaStore.MediaColumns.DISPLAY_NAME}, null, null, null);
                cursor.moveToFirst();
                result = cursor.getColumnCount() > 0 ? cursor.getString(0) : null;
            } catch (Exception e) {
                Log.d("My", "cursor error: " + e.getMessage());
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            Log.d("My", "cursor result: " + result);
            return result;
        } catch (Exception e) {
            return null;
        }


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