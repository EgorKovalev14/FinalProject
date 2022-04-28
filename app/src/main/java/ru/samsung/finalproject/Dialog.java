package ru.samsung.finalproject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.io.File;


public class Dialog extends DialogFragment implements View.OnClickListener {
    EditText editText;
    Button btn1;
    Button btn2;
    String stringForEditText;
    DBBooks dbBooks;
    int element_id;
    Context context;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Изменение названия");
        View v = inflater.inflate(R.layout.dialog_fragment, null);
        editText=v.findViewById(R.id.editTextTextPersonName);
        btn1=v.findViewById(R.id.buttonDialog1);
        btn2=v.findViewById(R.id.buttonDialog2);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        editText.setHint(stringForEditText);
        dbBooks = new DBBooks(context);
        return v;
    }

    public Dialog(String stringForEditText, int element_id, Context context) {
        this.stringForEditText = stringForEditText;
        this.element_id = element_id;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonDialog1:
                dismiss();
            case R.id.buttonDialog2:
                File file = new File("storage/emulated/0/"+stringForEditText);
                File newFile = new File("storage/emulated/0/"+editText.getText()+".txt");
                Log.d("FILETAG", stringForEditText + " stringForEditText");
                Log.d("FILETAG", editText.getText() + " editText.getText");
                Log.d("FILETAG", "file.exists "+file.exists());

                if(file.renameTo(newFile)){
                    Toast.makeText(context,"Файл был успешно переименован!", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(context,"Ошибка! Файл не был переименован!", Toast.LENGTH_LONG).show();
                }
                MainActivity.books.get(element_id).setName(String.valueOf(editText.getText()));
                MainActivity.books.get(element_id).setFilePath("/"+editText.getText());
                //dbBooks.update()
                dismiss();
        }
    }
}
