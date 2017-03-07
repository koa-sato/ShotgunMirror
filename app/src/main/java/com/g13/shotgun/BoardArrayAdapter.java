package com.g13.shotgun;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;

/**
 * Created by koasato on 3/2/17.
 */

public abstract class BoardArrayAdapter<Post> extends ArrayAdapter<Post> implements Filterable {

    protected ArrayList<Post> postList;

    public BoardArrayAdapter(Context context, int listItem, int textViewResourceId, ArrayList<Post> objects) {
        super(context, listItem, textViewResourceId, objects);
        postList = objects;
    }

    public void add(Post object) {
        postList.add(object);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return postList.size();
    }

    @Override
    public Post getItem(int position) {
        return postList.get(position);
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public abstract class ArrayAdapterFilter extends Filter {

        @Override
        protected abstract FilterResults performFiltering (CharSequence constraint);


        @Override
        protected void publishResults (CharSequence constraint, FilterResults results){
            postList = (ArrayList<Post>) results.values;
            if (results.count > 0)
                notifyDataSetChanged();
            else
                notifyDataSetInvalidated();
        }
    }
}
