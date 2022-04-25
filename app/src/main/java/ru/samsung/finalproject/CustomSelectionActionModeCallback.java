package ru.samsung.finalproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class CustomSelectionActionModeCallback implements ActionMode.Callback {
    DBQuotes dbQuotes;

    public CustomSelectionActionModeCallback(Context context){
         dbQuotes= new DBQuotes(context);
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
            menu.add(0,12345, 0, "Quote");

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
                String bookName = ReaderActivity.intent_file_path.substring(ReaderActivity.intent_file_path.
                        lastIndexOf("/")+1,ReaderActivity.intent_file_path.
                        lastIndexOf(".") );
                String quote = String.valueOf(ReaderActivity.editText.getText().subSequence
                        (ReaderActivity.editText.getSelectionStart(),ReaderActivity.editText.getSelectionEnd()));

                dbQuotes.insert(bookName, quote);


                actionMode.finish();


        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {

    }
}
