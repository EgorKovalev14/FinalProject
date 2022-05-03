package ru.samsung.finalproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReaderActivity extends AppCompatActivity {
    public static String intent_file_path;
    public static MyEditText editText;
    SharedPreferences sharedPreferences;
    StringBuilder str;
    File file;
    File file1;
    final static int MY_PERMISSION_REQUEST = 1;
    final String SAVED_ID = "ID";
    Integer id_from_intent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reader_activity);
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
        editText = findViewById(R.id.editText);
        file = Environment.getExternalStorageDirectory();
        file1 = new File(file, intent_file_path);
        List<String> pages = readFile(file1.getName());
        Log.d("ReadFile", "file1.name = "+file1.getName());
        String page = readPage(pages, 0);
        editText.setText(pages, 0);
        Log.d("ReadFile", "text input");
        Log.d("ReadFile", "string size: " + pages.size());
        Log.d("ReadFile", page);
        editText.setCustomSelectionActionModeCallback(new CustomSelectionActionModeCallback(this));
        editText.setShowSoftInputOnFocus(false);
        editText.setTextIsSelectable(true);
        Log.d("PREFTAG", "hasVisited " + hasVisited);
        Log.d("PREFTAG", "id_from_intent" + id_from_intent);


        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("hasVisited", true);
        editor.commit();


    }

    void saveData() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SAVED_ID,id_from_intent );
        Log.d("PREFTAG", String.valueOf(id_from_intent));
        editor.commit();
        Log.d("PREFTAG", "saveData");

    }
//    void loadData() {
//        sharedPreferences = getPreferences(MODE_PRIVATE);
//        Integer savedScrollX = sharedPreferences.getInt(SAVED_SCROLLX, 0);
//        Log.d("PREFTAG", String.valueOf(savedScrollX));
//        Integer savedScrollY = sharedPreferences.getInt(SAVED_SCROLLY, 0);
//        Log.d("PREFTAG", String.valueOf(savedScrollY));
//        scrollView.scrollTo(savedScrollX, savedScrollY);
//        Log.d("PREFTAG", "loadData");
//    }
    @Override
    protected void onDestroy() {
        saveData();
        super.onDestroy();
    }
    private List<String> readFile(String name){
        File storageDirectory = Environment.getExternalStorageDirectory();
        File downloadDirectory = new File(storageDirectory, "Download");
        File file = new File(downloadDirectory, name);
        if (file.exists() && file.canRead()){
            Scanner scanner = null;
            try {
                scanner = new Scanner(file);
                int line = 0;
                List<String> pages = new ArrayList<>();
                String page = "";
                while (scanner.hasNext()){
                    page += scanner.nextLine()+"\n";
                    line++;
                    if (line > 20){
                        pages.add(page);
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

    private String readPage(List<String> pages, int page){
        return pages.get(page);
    }
}

