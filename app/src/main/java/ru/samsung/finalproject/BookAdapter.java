package ru.samsung.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class BookAdapter extends BaseAdapter {
    ArrayList<BookItem> list;
    LayoutInflater inflater;

    public BookAdapter(Context context, ArrayList<BookItem> list) {
        this.list=list;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=inflater.inflate(R.layout.book_adapter_item, viewGroup, false);

        BookItem bookItem = (BookItem)getItem(i);

        TextView book_name = view.findViewById(R.id.book_name);
        book_name.setText(bookItem.getName());

        CheckBox book_checkBox = view.findViewById(R.id.book_checkBox);
        book_checkBox.setChecked(bookItem.isRead());

        return view;
    }




}
