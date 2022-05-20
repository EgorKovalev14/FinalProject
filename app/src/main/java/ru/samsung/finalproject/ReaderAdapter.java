package ru.samsung.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ReaderAdapter extends BaseAdapter {
    ArrayList<ReaderItem> list;
    LayoutInflater inflater;
    Context context;
    int text_size;

    public ReaderAdapter(Context context, ArrayList<ReaderItem> list, int text_size) {
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.text_size=text_size;
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
        view = inflater.inflate(R.layout.reader_activity_item, viewGroup, false);

        ReaderItem readerItem = (ReaderItem) getItem(i);

        TextView readable_name = view.findViewById(R.id.readable);
        readable_name.setText(readerItem.getReadable());
        readable_name.setTextIsSelectable(true);
        readable_name.setCustomSelectionActionModeCallback(new CustomSelectionActionModeCallback(context, readable_name));
        readable_name.setShowSoftInputOnFocus(false);
        readable_name.setTextSize(text_size);

        return view;
    }


}
