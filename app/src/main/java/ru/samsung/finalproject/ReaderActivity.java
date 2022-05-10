package ru.samsung.finalproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReaderActivity extends AppCompatActivity {
    public static String intent_file_path;
    SharedPreferences sharedPreferences;
    StringBuilder str;
    File file;
    File file1;
    final static int MY_PERMISSION_REQUEST = 1;
    Integer id_from_intent;
    static ListView listView;
    BaseAdapter adapter;
    DBBooks dbBooks;
    int scroll;
    String name;
    int content_id;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reader_activity);
        listView=findViewById(R.id.reader_list);
        dbBooks=new DBBooks(this);
        sharedPreferences = getPreferences(MODE_PRIVATE);
        boolean hasVisited = sharedPreferences.getBoolean("hasVisited", false);
        str = new StringBuilder();
        if (!PermissionUtils.hasPermissions(this)) {
            PermissionUtils.requestPermissions(this, MY_PERMISSION_REQUEST);
        } else {
            Log.d("TAQwerty", "разрешение дано!");
        }
        intent_file_path = (String) getIntent().getSerializableExtra("INTENT_FILE_PATH");
        id_from_intent = (Integer) getIntent().getSerializableExtra("INTENT_ID");
        content_id = (Integer) getIntent().getSerializableExtra("CONTENT_ID");
        scroll = dbBooks.selectBookByContent(content_id).getScroll();
        int id2 = dbBooks.selectBookByContent(content_id).getId();
        Log.d("READTAG", "id2: "+id2);
        name=(String) getIntent().getSerializableExtra("NAME");
        file = Environment.getExternalStorageDirectory();
        file1 = new File(file, intent_file_path);
        ArrayList<ReaderItem> pages = readFile(file1.getName());
        adapter = new ReaderAdapter(this,pages);

        listView.setAdapter(adapter);
        dbBooks=new DBBooks(this);
        loadData();




    }

    void saveData() {
        BookFromDB bookFromDB = new BookFromDB(id_from_intent, name, content_id, listView.getScrollY() );
        Log.d("READTAG", "save data scroll "+listView.getScrollY());
        Log.d("READTAG", "save data id_from_intent "+id_from_intent);
        dbBooks.update(bookFromDB);
    }
    void loadData() {
        Log.d("READTAG", "loadData: "+ scroll);
        if(scroll!=-1){
            listView.scrollTo(0,scroll);

        }


    }
    @Override
    protected void onDestroy() {
        saveData();
        super.onDestroy();
    }
    private ArrayList<ReaderItem> readFile(String name){
        File storageDirectory = Environment.getExternalStorageDirectory();
        File downloadDirectory = new File(storageDirectory, "Download");
        File file = new File(downloadDirectory, name);
        if (file.exists() && file.canRead()){
            Scanner scanner = null;
            try {
                scanner = new Scanner(file);
                int line = 0;
                ArrayList<ReaderItem> pages = new ArrayList<>();
                String page = "";
                while (scanner.hasNext()){
                    page += scanner.nextLine()+"\n";
                    line++;
                    if (line > 19){
                        pages.add(new ReaderItem(page));
                        page = "";
                        line = 0;
                    }
                }
                scanner.close();
                Log.d("ReadFile", "read file OK");
                return pages;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }

        } else {
            return null;
        }
    }

    private String readPage(ArrayList<ReaderItem> pages, int page){
        return pages.get(page).getReadable();
    }
}

