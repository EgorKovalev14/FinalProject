package ru.samsung.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class QuoteAdapter extends BaseAdapter {
    ArrayList<QuoteItem> list;
    LayoutInflater inflater;

    public QuoteAdapter(Context context, ArrayList<QuoteItem> list) {
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
        view=inflater.inflate(R.layout.quote_adapter_item, viewGroup, false);

        QuoteItem quoteItem = (QuoteItem) getItem(i);

        TextView quote_name = view.findViewById(R.id.quote_name);
        quote_name.setText(quoteItem.getQuote());



        return view;
    }




}
