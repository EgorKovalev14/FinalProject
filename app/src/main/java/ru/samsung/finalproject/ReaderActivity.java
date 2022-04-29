package ru.samsung.finalproject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.EditText;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.Scanner;

public class ReaderActivity extends AppCompatActivity {
    public static String intent_file_path;
    public static EditText editText;
    public static final String APP_PREFERENCES = "mysettings";
    StringBuilder str;
    File file;
    File file1;
    final static int MY_PERMISSION_REQUEST = 1;
    Scanner in;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reader_activity);
        str= new StringBuilder();

        if (!PermissionUtils.hasPermissions(this)) {
            PermissionUtils.requestPermissions(this, MY_PERMISSION_REQUEST);
        } else {
            Log.d("TAQwerty", "разрешение дано!");
        }

        intent_file_path = (String) getIntent().getSerializableExtra("INTENT_FILE_PATH");
        editText = findViewById(R.id.editText);
        file = Environment.getExternalStorageDirectory();
        file1 = new File(file, intent_file_path);
        Log.d("FILETAG", file1.getAbsolutePath());
        try {
            in= new Scanner(file1);
            while(in.hasNext()){
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



    }
    protected void saveActivityPreferences() {
        // Создайте или извлеките объект настроек активности.
        SharedPreferences activityPreferences = getPreferences(Activity.MODE_PRIVATE);
        // Извлеките редактор, чтобы изменить Общие настройки.
        SharedPreferences.Editor editor = activityPreferences.edit();
        // Запишите новые значения примитивных типов в объект Общих настроек.
        editor.putString("currentTextValue", editText.getText().toString());
        // Сохраните изменения.
        editor.commit();
    }
//    private boolean LoadPreferences() {
//        SharedPreferences sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
//        if(sharedPreferences=null){
//            return false;
//        }else{
//            return true;
//        }
//
//    }
    @Override
    protected void onDestroy() {
        saveActivityPreferences();
        super.onDestroy();
    }
}
