package com.g13.shotgun.DriveBoard;

import android.widget.Filter;

import java.util.ArrayList;

public class ArrayAdapterFilter2 extends Filter {
    private ArrayList<DriveBoardPost> postList;
    public boolean changed = false;

    public ArrayAdapterFilter2(ArrayList<DriveBoardPost> _postList) {
        super();
        postList = _postList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        changed = false;
        FilterResults filterResults = new FilterResults();
        ArrayList<DriveBoardPost> filteredList = new ArrayList<DriveBoardPost>();

        if(constraint != null && postList!=null) {
            int length=postList.size();
            int i=0;
            while(i<length){
                DriveBoardPost post = postList.get(i);

                String city = post.get_city();
                String day = post.get_month() + "/" + post.get_day() + "/" + post.get_year();

                String sequence = constraint.toString();
                if (post.toString().contains(sequence))
                    filteredList.add(post);

                i++;
            }
            //following two lines is very important
            //as publish result can only take FilterResults objects
            filterResults.values = filteredList;
            filterResults.count = filteredList.size();
        }
        return filterResults;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void publishResults(CharSequence contraint, FilterResults results) {
        postList = (ArrayList<DriveBoardPost>) results.values;
        if (results.count > 0)
            changed = true;
        else
            changed = false;
    }
}
