package ru.samsung.finalproject;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    Dialog dlg;
    ListView listView;
    DBBooks dbBooks;
    private static final int PERMISSION_STORAGE = 101;
    static ArrayList<BookItem> books;
    BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbBooks = new DBBooks(this);
        if (!PermissionUtils.hasPermissions(MainActivity.this)) {
            PermissionUtils.requestPermissions(MainActivity.this, PERMISSION_STORAGE);
        }
        books = new ArrayList<>();
        adapter = new BookAdapter(this, books);
        listView = findViewById(R.id.list);
        registerForContextMenu(listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        try {

            for(int i = 0; i<dbBooks.selectAll().size(); i++){
                String b = dbBooks.selectAll().get(i).getBookFromDBName();
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.change_name:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                Log.d("INFOTAG", "id1:"+info.position);
                String s = ((BookItem)listView.getAdapter().getItem(info.position)).getName();
                dlg = new Dialog(s, info.position, getApplicationContext());
                dlg.show(getSupportFragmentManager(), "dlg");

                break;


            case R.id.delete_book:
                AdapterView.AdapterContextMenuInfo info1 = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                Log.d("TAGQWERTY", info1.toString());
                books.remove(info1.position);
                dbBooks.delete(info1.position+1);
                adapter.notifyDataSetChanged();

                break;
        }return super.onContextItemSelected(item);
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
        intent.putExtra("INTENT_ID", i);
        startActivity(intent);
    }

}