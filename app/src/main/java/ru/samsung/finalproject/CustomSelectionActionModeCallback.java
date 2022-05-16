package ru.samsung.finalproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomSelectionActionModeCallback implements ActionMode.Callback {
    DBQuotes dbQuotes;
    TextView textView;

    public CustomSelectionActionModeCallback(Context context, TextView textView){
        dbQuotes= new DBQuotes(context);
        this.textView=textView;
    }
    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        Log.d("ActionTag", "onCreateActionMode started");

        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        Log.d("ActionTag", "onPrepareActionMode started");
        try{
            MenuItem copyItem=menu.findItem(android.R.id.copy);
            CharSequence title = copyItem.getTitle();
            menu.clear();
            menu.add(0,android.R.id.copy, 0, title);
            menu.add(0,12345, 0, "Цитата");

        }catch(Exception e){
            Log.d("ActionTag", "что-то пошло не так"+e.getMessage());
            e.printStackTrace();

        }
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case 12345:
                String bookName = ReaderActivity.bookItem.getName();
                try {
                    String quote = String.valueOf(textView.getText().subSequence
                            (textView.getSelectionStart(),textView.getSelectionEnd()));
                    Log.d("INFOTAG", quote);
                    dbQuotes.insert(bookName, quote);

                }catch(NullPointerException e){
                    Log.d("INFOTAG", e.getMessage());
                }
                actionMode.finish();
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {

    }

}
