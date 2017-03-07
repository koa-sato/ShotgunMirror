package com.g13.shotgun.rideboard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Filter;
import android.widget.Filterable;

import com.g13.shotgun.BoardArrayAdapter;

import java.util.ArrayList;


/**
 * Created by koasato on 3/2/17.
 */

public class RideBoardArrayAdapter<RideBoardPost> extends BoardArrayAdapter<RideBoardPost> implements Filterable {

    private ArrayAdapterFilter mFilter;


    public RideBoardArrayAdapter(Context context, int listItem, int textViewResourceId, ArrayList<RideBoardPost> objects) {
        super(context, listItem, textViewResourceId, objects);
        postList = objects;
    }

    @Override
    public RideBoardPost getItem(int position) {
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
                final ArrayList<RideBoardPost> list;
                synchronized (constraint) {
                    list = new ArrayList<>(postList);
                }
                results.values = list;
                results.count = list.size();
            } else {
                final String prefixString = constraint.toString().toLowerCase();

                final ArrayList<RideBoardPost> values;
                synchronized (constraint) {
                    values = new ArrayList<>(postList);
                }

                final int count = values.size();
                final ArrayList<RideBoardPost> newValues = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                    final RideBoardPost value = values.get(i);
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
            postList = (ArrayList<RideBoardPost>) results.values;
            if (results.count > 0)
                notifyDataSetChanged();
            else
                notifyDataSetInvalidated();
        }
    }
}
