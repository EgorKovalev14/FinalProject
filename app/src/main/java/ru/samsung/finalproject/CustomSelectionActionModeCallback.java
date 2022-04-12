package ru.samsung.finalproject;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

public class CustomSelectionActionModeCallback implements ActionMode.Callback {
    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        try{
            MenuItem copyItem=menu.findItem(android.R.id.copy);
            CharSequence title = copyItem.getTitle();
            menu.clear();
            menu.add(0,android.R.id.copy, 0, title);
        }catch(Exception e){

        }
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case android.R.id.copy:

        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {

    }
}
