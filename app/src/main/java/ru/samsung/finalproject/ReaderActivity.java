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
    final String SAVED_TEXT = "TEXT";
    final String SAVED_SCROLLX = "SCROLLX";
    final String SAVED_SCROLLY = "SCROLLY";
    private static final String MY_SETTINGS = "my_settings";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reader_activity);
        SharedPreferences sp = getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE);
        boolean hasVisited = sp.getBoolean("hasVisited", false);
        if(hasVisited){
            loadData();
        }else {
            str = new StringBuilder();
            if (!PermissionUtils.hasPermissions(this)) {
                PermissionUtils.requestPermissions(this, MY_PERMISSION_REQUEST);
            } else {
                Log.d("TAQwerty", "разрешение дано!");
            }

            intent_file_path = (String) getIntent().getSerializableExtra("INTENT_FILE_PATH");
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
        }
        editText.setCustomSelectionActionModeCallback(new CustomSelectionActionModeCallback(this));
        editText.setShowSoftInputOnFocus(false);



    }
    void saveData() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVED_TEXT, editText.getText().toString());
        editor.putInt(SAVED_SCROLLX, scrollView.getScrollX());
        editor.putInt(SAVED_SCROLLY, scrollView.getScrollY());
        editor.commit();
    }

    void loadData() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        String savedText = sharedPreferences.getString(SAVED_TEXT, "");
        Integer savedScrollX = sharedPreferences.getInt(SAVED_SCROLLX, 0);
        Integer savedScrollY = sharedPreferences.getInt(SAVED_SCROLLY, 0);
        editText.setText(savedText);
        scrollView.scrollTo(savedScrollX, savedScrollY);
    }
    @Override
    protected void onDestroy() {
        saveData();
        super.onDestroy();
    }
}
