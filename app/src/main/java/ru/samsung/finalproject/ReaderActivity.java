package ru.samsung.finalproject;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
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

public class ReaderActivity extends AppCompatActivity{
    String intent_file_path;
    TextView myTextView;
    StringBuilder str;
    File file;
    String[] list;
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reader_activity);
        intent_file_path = (String) getIntent().getSerializableExtra("INTENT_FILE_PATH");
        myTextView =findViewById(R.id.textView);
        readFile(intent_file_path);
        file = Environment.getExternalStorageDirectory();
        Log.d("TAG", String.valueOf(str));
        myTextView.setText(String.valueOf(str));
    }

     void readFile(String fileName) {
        try {
            File externalStorage = Environment.getExternalStorageDirectory();
            File file = new File(externalStorage, fileName);
            str = new StringBuilder();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                Log.d("File", scanner.nextLine());
                str.append(scanner.nextLine());
            }
        } catch (Exception e) {
            Log.e("EXCEPTION", e.toString() );
        }

    }

}
