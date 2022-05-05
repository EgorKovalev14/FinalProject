package ru.samsung.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ReaderAdapter extends BaseAdapter implements View.OnClickListener {
    ArrayList<ReaderItem> list;
    LayoutInflater inflater;
    Context context;
    static View view;

    public ReaderAdapter(Context context, ArrayList<ReaderItem> list) {
        this.list=list;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
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
        view=inflater.inflate(R.layout.reader_activity_item, viewGroup, false);

        ReaderItem readerItem = (ReaderItem) getItem(i);

        TextView readable_name = view.findViewById(R.id.readable);
        readable_name.setOnClickListener(this);
        readable_name.setText(readerItem.getReadable());
        readable_name.setTextIsSelectable(true);
        readable_name.setCustomSelectionActionModeCallback(new CustomSelectionActionModeCallback(context));
        readable_name.setShowSoftInputOnFocus(false);

        return view;
    }


    @Override
    public void onClick(View view) {
        this.view = view;
    }
}

