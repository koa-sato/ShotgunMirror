package com.g13.shotgun.DriveBoard;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Dominic on 2/27/2017.
 */

public class DriveBoardAdapter extends ArrayAdapter<DriveBoardPost> {

    public DriveBoardAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public DriveBoardAdapter(Context context, int resource, int textViewResourceId, List<DriveBoardPost> items) {
        super(context, resource, items);
    }

    @Override
    public int getCount(){
        return super.getCount();
    }

    @Override
    public View getView(int i, View v, ViewGroup vg){
        return null;
    }

    @Override
    public long getItemId(int p){
        DriveBoardPost po = getItem(p);
        return Integer.parseInt(po.get_key());
    }
}
