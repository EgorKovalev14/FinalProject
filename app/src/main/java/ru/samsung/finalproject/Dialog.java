package ru.samsung.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;


public class Dialog extends DialogFragment implements View.OnClickListener {
    EditText editText;
    Button btn1;
    Button btn2;
    String stringForEditText;
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
        return v;
    }

    public Dialog(String stringForEditText) {
        this.stringForEditText = stringForEditText;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonDialog1:
                dismiss();
            case R.id.buttonDialog2:

        }
    }
}
