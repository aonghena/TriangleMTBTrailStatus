package com.example.a.trianglemtbtrailstatus;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.*;
import  java.util.*;
import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.*;

public class CustomArrayAdapter extends ArrayAdapter<String> {

        private int layoutResource;

        public CustomArrayAdapter(Context context, int layoutResource, List<String> threeStringsList) {
            super(context, layoutResource, threeStringsList);
            this.layoutResource = layoutResource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = convertView;

            if (view == null) {
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                view = layoutInflater.inflate(layoutResource, null);
            }

            String threeStrings = getItem(position);

            TextView t = (TextView) view.findViewById(R.id.text1);
            t.setText(threeStrings);
            t.setBackgroundColor(Color.parseColor("#BDBDBD"));
            if(threeStrings.equals("OPEN")){
                // t.setBackgroundColor(Color.parseColor("#81D4FA"));
                t.setTextColor(Color.parseColor("#01579B"));
            }else if(threeStrings.equals("CLOSED")){
                t.setTextColor(Color.parseColor("#B71C1C"));
                // t.setBackgroundColor(Color.parseColor("#EF9A9A"));
            }


            return view;
        }
    }