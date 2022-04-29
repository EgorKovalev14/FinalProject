package ru.samsung.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.EditText;
import android.widget.ScrollView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.Scanner;

public class ReaderActivity extends AppCompatActivity {
    public static String intent_file_path;
    public static EditText editText;
    SharedPreferences sharedPreferences;
    StringBuilder str;
    ScrollView scrollView;
    File file;
    File file1;
    final static int MY_PERMISSION_REQUEST = 1;
    Scanner in;
    final String SAVED_ID = "ID";
    final String SAVED_SCROLLX = "SCROLLX";
    final String SAVED_SCROLLY = "SCROLLY";
    Integer id_from_intent;
    private static final String MY_SETTINGS = "my_settings";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reader_activity);
        SharedPreferences sp = getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE);
        boolean hasVisited = sp.getBoolean("hasVisited", false);
        str = new StringBuilder();
        if (!PermissionUtils.hasPermissions(this)) {
            PermissionUtils.requestPermissions(this, MY_PERMISSION_REQUEST);
        } else {
            Log.d("TAQwerty", "разрешение дано!");
        }
        intent_file_path = (String) getIntent().getSerializableExtra("INTENT_FILE_PATH");
        id_from_intent = (Integer) getIntent().getSerializableExtra("INTENT_ID");
        editText = findViewById(R.id.editText);
        scrollView = findViewById(R.id.scrollView);
        file = Environment.getExternalStorageDirectory();
        file1 = new File(file, intent_file_path);
        Log.d("FILETAG", file1.getAbsolutePath());
        try {
            in = new Scanner(file1);
            while (in.hasNext()) {
                str.append(in.nextLine());
                str.append(System.getProperty("line.separator"));
            }
            in.close();
        } catch (Exception e) {
            Log.d("TAQwerty", String.valueOf(e.getMessage()));
        }
        editText.setText(String.valueOf(str));
        editText.setCustomSelectionActionModeCallback(new CustomSelectionActionModeCallback(this));
        editText.setShowSoftInputOnFocus(false);
        if(hasVisited && id_from_intent==sharedPreferences.getInt(SAVED_ID, 0)){
            Log.d("PREFTAG", "loadData");
            loadData();
        }



    }


    void saveData() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SAVED_ID,id_from_intent );
        Log.d("PREFTAG", String.valueOf(id_from_intent));
        editor.putInt(SAVED_SCROLLX, scrollView.getScrollX());
        editor.putInt(SAVED_SCROLLY, scrollView.getScrollY());
        editor.commit();
        Log.d("PREFTAG", "saveData");

    }

    void loadData() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        Integer savedScrollX = sharedPreferences.getInt(SAVED_SCROLLX, 0);
        Integer savedScrollY = sharedPreferences.getInt(SAVED_SCROLLY, 0);
        scrollView.scrollTo(savedScrollX, savedScrollY);
        Log.d("PREFTAG", "loadData");
    }
    @Override
    protected void onDestroy() {
        saveData();
        super.onDestroy();
    }
}
