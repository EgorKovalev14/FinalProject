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
import java.util.ArrayList;


public class Dialog extends DialogFragment implements View.OnClickListener {
    EditText editText;
    Button btn1;
    Button btn2;
    String stringForEditText;
    DBBooks dbBooks;
    BookFromDB bookFromDB;
    int element_id;
    Context context;

    public void setDbBooks(DBBooks dbBooks) {
        this.dbBooks = dbBooks;
    }

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
                File file = new File("storage/emulated/0/Download/"+stringForEditText+".txt");
                File newFile = new File("storage/emulated/0/Download/"+editText.getText().toString()+".txt");
                if(file.renameTo(newFile)){
                    Toast.makeText(context,"Файл был успешно переименован!", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(context,"Ошибка! Файл не был переименован!", Toast.LENGTH_LONG).show();
                }
                MainActivity.books.get(element_id).setName(String.valueOf(editText.getText()));
                MainActivity.books.get(element_id).setFilePath("/Download/"+editText.getText()+".txt");
                bookFromDB = new BookFromDB(element_id+1,editText.getText().toString()+".txt", MainActivity.books.get(element_id).getContent_id(), MainActivity.books.get(element_id).getScroll() );
                dbBooks.update(bookFromDB);
                ArrayList<BookFromDB> arrayList=dbBooks.selectAll();
                for(int i = 0; i<arrayList.size();i++){
                    Log.d("FILETAG",arrayList.get(i)+"   "+ arrayList.get(i).id);
                    Log.d("FILETAG", bookFromDB.bookFromDBName);
                }
                dismiss();
        }
    }
}
