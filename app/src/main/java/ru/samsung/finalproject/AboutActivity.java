package ru.samsung.finalproject;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {
    TextView textView123;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        textView123 =findViewById(R.id.textView_about);
        textView123.setTextSize(20);
        textView123.setTextIsSelectable(true);

    }
}
