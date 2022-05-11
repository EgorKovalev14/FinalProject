package ru.samsung.finalproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<BookItem> {
    ArrayList<BookItem> list;

    public BookAdapter(Context context, ArrayList<BookItem> list) {
        super(context, R.layout.book_adapter_item, list);
        this.list=list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.book_adapter_item, parent, false);
        }

        BookItem bookItem = list.get(position);

        TextView book_name = convertView.findViewById(R.id.book_name);
        book_name.setText(bookItem.getName());

        CheckBox book_checkBox = convertView.findViewById(R.id.book_checkBox);
        book_checkBox.setChecked(bookItem.isRead());

        return convertView;
    }
}
