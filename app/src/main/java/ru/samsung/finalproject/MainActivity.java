package ru.samsung.finalproject;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
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
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    Dialog dlg;
    ListView listView;
    DBBooks dbBooks;
    private static final int PERMISSION_STORAGE = 101;
    static ArrayList<BookItem> books;
    int content_id;
    BookItem bookItem;
    int text_size = 18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbBooks = new DBBooks(this);
        if (!PermissionUtils.hasPermissions(MainActivity.this)) {
            PermissionUtils.requestPermissions(MainActivity.this, PERMISSION_STORAGE);
        } else {
            getBooksList();
        }

    }

    private void getBooksList(){
        books = new ArrayList<>();
        listView = findViewById(R.id.list);
        registerForContextMenu(listView);
        listView.setOnItemClickListener(this);

        try {
            books = dbBooks.selectAll();
            Log.d("MYTAG", "list size: " + books.size());
            for(int i = 0; i<books.size(); i++){
                BookItem item = books.get(i);
                String b = item.getName();
                Log.d("MYTAG", "name "+item.getName());
                if(b.contains(".")){
                    item.setName(b.substring(0, b.lastIndexOf(".")));
                }else{
                    item.setName(b);
                }
                item.setFilePath("/" + b);
            }

            if (getIntent().getData() != null ) {
                Log.d("MYTAG", "intent " + getIntent().getData());
                String name = getCursorValue();
                String s_content_id=getIntent().getData().toString().substring(getIntent().getData().toString().lastIndexOf("/") + 1);
                if(s_content_id.charAt(0)>'0'&&s_content_id.charAt(0)<'9'){
                    content_id = Integer.parseInt(getIntent().getData().toString().substring(getIntent().getData().toString().lastIndexOf("/") + 1));
                    bookItem = findBookInList(content_id);
                }else{
                    bookItem = findBookInList(s_content_id);
                }
                Log.d("MYTAG", "name " + name);
                Log.d("MYTAG", "content_id " + content_id);

                if (bookItem == null){
                    int scroll = 0;
                    books.add(new BookItem(name.substring(0, name.lastIndexOf(".")), false, "/" + name, content_id,scroll ));
                    dbBooks.insert(name, content_id, scroll);
                }
            }
        }catch(Exception e){
            Log.d("MYTAG", "Ошибка чтения");
            Log.d("MYTAG", e.getMessage());
        }

        BookAdapter adapter = new BookAdapter(this, books);
        listView.setAdapter(adapter);
    }

    private BookItem findBookInList(int contentId){
        boolean find = false;
        for (int i = 0; i < books.size(); i++) {
            BookItem bookItem = books.get(i);
            if (bookItem.getContent_id() == contentId){
                return bookItem;
            }
        }
        return null;
    }
    private BookItem findBookInList(String sContentId){
        for (int i = 0; i < books.size(); i++) {
            BookItem bookItem = books.get(i);
            if (bookItem.getName() == sContentId){
                return bookItem;
            }
        }
        return null;
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
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()){
            case R.id.change_name:
                Log.d("INFOTAG", "id1:"+info.position);
                String s = ((BookItem)listView.getAdapter().getItem(info.position)).getName();
                dlg = new Dialog(s, info.position, getApplicationContext(), ((BookItem) listView.getAdapter().getItem(info.position)).getContent_id(),((BookItem)listView.getAdapter().getItem(info.position)).getScroll());
                dlg.setDbBooks(dbBooks);
                dlg.show(getSupportFragmentManager(), "dlg");

                break;


            case R.id.delete_book:
                Log.d("TAGQWERTY", info.toString());
                books.remove(info.position);
                dbBooks.delete(info.position+1);
                BookAdapter adapter = (BookAdapter) listView.getAdapter();
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
            case R.id.text_big:
                text_size=22;
            case R.id.text_medium:
                text_size=18;
            case R.id.text_small:
                text_size=14;
            case R.id.about_app:
                Intent intent1 = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent1);

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
                if (cursor.getColumnCount() > 0){
                    result = cursor.getString(0);
                }
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
                    getBooksList();
                } else {
                    Toast.makeText(this, "permission failed", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        BookItem info = (BookItem) adapterView.getAdapter().getItem(i);
        String intent_file_path = info.getFilePath();
        Intent intent = new Intent(MainActivity.this, ReaderActivity.class);
        intent.putExtra("Position", i);
        intent.putExtra("TEXT_SIZE", text_size);
        startActivity(intent);
    }
}