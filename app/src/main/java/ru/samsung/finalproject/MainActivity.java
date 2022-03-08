package ru.samsung.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    final static int MY_PERMISSION_REQUEST = 1;
    private static final int PERMISSION_STORAGE = 101;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
        }
        if (!PermissionUtils.hasPermissions(MainActivity.this)){
            PermissionUtils.requestPermissions(MainActivity.this, PERMISSION_STORAGE);
        }
        PermissionUtils.requestPermissions(MainActivity.this, PERMISSION_STORAGE);
        ArrayList<BookItem> books = new ArrayList<>();
        listView = findViewById(R.id.list);

        books.add(new BookItem("Правила проведения соревнований", false, "Rutkovskiy_Dzhuri_1_Dzhuri-kozaka-Shvayki_RuLit_Net.txt"));
        books.add(new BookItem("Пролетая над гнездом кукушки", false, ""));
        books.add(new BookItem("Вечера на хуторе близ Диканьки", false, ""));

        BaseAdapter adapter = new BookAdapter(this, books);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reader_menu, menu);
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