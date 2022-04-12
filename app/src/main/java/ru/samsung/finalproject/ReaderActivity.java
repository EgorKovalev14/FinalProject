package ru.samsung.finalproject;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class ReaderActivity extends AppCompatActivity {
    String intent_file_path;
    EditText editText;
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
        Log.d("TAQwerty", file1.toString()+ " " + file1.exists());
        try {
            Log.d("TAQwerty", "try-catch starts"+" "+ file1.exists());
            in= new Scanner(file1);
            Log.d("TAQwerty", "hasNext " + in.hasNext());
            while(in.hasNext()){
                str.append(in.nextLine());
            }
//            Log.d("TAQwerty", "конец трай кетч");
//            Log.d("TAQwerty", "результат: " + str);
            in.close();
        } catch (Exception e) {
              Log.d("TAQwerty", String.valueOf(e.getMessage()));
              Log.d("TAQwerty", "что то пошло не так");
            e.printStackTrace();
        }
        editText.setText(String.valueOf(str));
        editText.setCustomSelectionActionModeCallback(new CustomSelectionActionModeCallback());
        editText.setShowSoftInputOnFocus(false);

//        myTextView.setMovementMethod(new ScrollingMovementMethod());


    }



}
