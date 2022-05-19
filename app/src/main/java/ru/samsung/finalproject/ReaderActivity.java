package ru.samsung.finalproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

public class ReaderActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    final static int MY_PERMISSION_REQUEST = 1;
    ListView listView;
    BaseAdapter adapter;
    DBBooks dbBooks;
    static BookItem bookItem;
    ArrayList<ReaderItem> pages;
    int text_size;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reader_activity);
        listView=findViewById(R.id.reader_list);
        dbBooks=new DBBooks(this);
        if (!PermissionUtils.hasPermissions(this)) {
            PermissionUtils.requestPermissions(this, MY_PERMISSION_REQUEST);
        } else {
            Log.d("TAQwerty", "СЂР°Р·СЂРµС€РµРЅРёРµ РґР°РЅРѕ!");
        }
        text_size = getIntent().getIntExtra("TEXT_SIZE", 1);
        if(getSupportActionBar()!=null)
            this.getSupportActionBar().hide();
        int position = getIntent().getIntExtra("Position", -1);
        Log.d("READTAG", "position  "+position);
        bookItem = MainActivity.books.get(position);
        Log.d("READTAG", "bookItem  "+bookItem.toString());
        File file = Environment.getExternalStorageDirectory();
        Log.d("FILETAG", "getExternalStorageDirectory - "+Environment.getExternalStorageDirectory());
        Log.d("FILETAG", "bookItem.getFilePath - "+bookItem.getFilePath());
        File file1 = new File(file, bookItem.getFilePath());
        Log.d("MYTAG", "file1.getName "+file1.getName());
        if(file1.getName().contains("/")){
            pages = readFile(file1.getName());

        }else{
            pages = readFile("/"+file1.getName());

        }
        adapter = new ReaderAdapter(this,pages);

        listView.setAdapter(adapter);
        dbBooks=new DBBooks(this);
        loadData();

        listView.setOnItemClickListener(this);


    }

    void saveData() {

//        View c = listView.getChildAt(0);
//        int scrollY = -c.getTop() + listView.getFirstVisiblePosition() * c.getHeight();
        int scrollY=listView.getFirstVisiblePosition();
        bookItem.setScroll(scrollY);
        Log.d("READTAG", "save data scroll "+scrollY);
        dbBooks.update(bookItem);
    }




    void loadData() {
        int scroll = bookItem.getScroll();
        Log.d("READTAG", "loadData: "+ scroll);
        if(scroll!=-1){
            listView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                    listView.smoothScrollToPositionFromTop(bookItem.getScroll(),0);
                }
            });
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
        if(!name.contains(".txt")){
            name=name+".txt";
        }
        File file = new File(downloadDirectory, name);
        Log.d("MYTAG", "download directory  " + file.getAbsolutePath());
        Log.d("MYTAG", "readFile: file exists  " + name+ " " + file.exists());
        Log.d("MYTAG", "readFile: file can read  " + name+ " " + file.canRead());
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
                    if (line > 10){
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        listView.scrollListBy(3000);
        Log.d("NIGTAG", "onItemClick: ");
    }
}
