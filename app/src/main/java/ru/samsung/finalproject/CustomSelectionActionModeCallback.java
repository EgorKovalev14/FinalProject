package ru.samsung.finalproject;

import android.content.Context;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

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
 //           Log.d("ActionTag", " try-catch started");
            MenuItem copyItem=menu.findItem(android.R.id.copy);
//            Log.d("ActionTag", " item is found");
            CharSequence title = copyItem.getTitle();
//            Log.d("ActionTag", "item title found");
            menu.clear();
//            Log.d("ActionTag", " menu cleared");
            menu.add(0,android.R.id.copy, 0, title);
            menu.add(0,12345, 0, "Quote");

//            Log.d("ActionTag", "item added, try-catch finished");
        }catch(Exception e){
            Log.d("ActionTag", "что-то пошло не так"+e.getMessage());
            e.printStackTrace();

        }
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case 12345:
                //dbQuotes.insert(actionMode.getTitle());
                Log.d("DBTAG", String.valueOf(menuItem.getActionProvider()));

        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {

    }
}
