package ru.samsung.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

public class Dialog extends DialogFragment implements View.OnClickListener {
    @Override
    public void onClick(View v) {

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Изменение названия");
        View v = inflater.inflate(R.layout.dialog_fragment, null);
        v.findViewById(R.id.buttonDialog1).setOnClickListener(this);
        v.findViewById(R.id.buttonDialog2).setOnClickListener(this);
        return v;
    }
}
