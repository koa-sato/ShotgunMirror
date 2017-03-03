package com.g13.shotgun.DriveBoard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;


/**
 * Created by koasato on 3/2/17.
 */

public class DriveBoardArrayAdapter<DriveBoardPost> extends ArrayAdapter<DriveBoardPost> implements Filterable {

    private ArrayList<DriveBoardPost> postList;
    private ArrayAdapterFilter mFilter;

    /*public DriveBoardArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        for (int i = 0; i < postList.size(); i++)
            postList.add(this.getItem(i));
        filter = new ArrayAdapterFilter2((ArrayList<T>) postList);
    }*/

    /*public DriveBoardArrayAdapter(Context context, LauncherActivity.ListItem list_item , Text text, int textViewResourceId) {
        super(context, textViewResourceId);
        for (int i = 0; i < postList.size(); i++)
            postList.add(this.getItem(i));
        filter = new ArrayAdapterFilter2((ArrayList<com.g13.shotgun.DriveBoard.DriveBoardPost>) postList);
    }*/


    public DriveBoardArrayAdapter(Context context, int listItem, int textViewResourceId, ArrayList<DriveBoardPost> objects) {
        super(context, listItem, textViewResourceId, objects);
        postList = objects;
    }

    public void add(DriveBoardPost object) {
        postList.add(object);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return postList.size();
    }

    @Override
    public DriveBoardPost getItem(int position) {
        return postList.get(position);
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


    @Override
    public @NonNull ArrayAdapterFilter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayAdapterFilter();
        }
        return mFilter;
    }

    public class ArrayAdapterFilter extends Filter {

        @Override
        protected FilterResults performFiltering (CharSequence constraint){

            final FilterResults results = new FilterResults();

            if (postList == null) {
                synchronized (constraint) {
                    postList = new ArrayList<>(postList);
                }
            }

            if (constraint == null || constraint.length() == 0) {
                final ArrayList<DriveBoardPost> list;
                synchronized (constraint) {
                    list = new ArrayList<>(postList);
                }
                results.values = list;
                results.count = list.size();
            } else {
                final String prefixString = constraint.toString().toLowerCase();

                final ArrayList<DriveBoardPost> values;
                synchronized (constraint) {
                    values = new ArrayList<>(postList);
                }

                final int count = values.size();
                final ArrayList<DriveBoardPost> newValues = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                    final DriveBoardPost value = values.get(i);
                    final String valueText = value.toString().toLowerCase();

                    // First match against the whole, non-splitted value
                    if (valueText.contains(prefixString)) {
                        newValues.add(value);
                    } else {
                        final String[] words = valueText.split(" ");
                        for (String word : words) {
                            if (word.startsWith(prefixString)) {
                                newValues.add(value);
                                break;
                            }
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }
            publishResults(constraint, results);
            return results;
        }

        @Override
        protected void publishResults (CharSequence constraint, FilterResults results){
            postList = (ArrayList<DriveBoardPost>) results.values;
            if (results.count > 0)
                notifyDataSetChanged();
            else
                notifyDataSetInvalidated();
        }
    }
}
