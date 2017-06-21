package com.jgive.kobieliasi.jgiveapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Kobi Eliasi on 21/01/2017.
 * Tithe List View Adapter
 */

public class TitheListViewAdapter extends ArrayAdapter {
    private ArrayList<String> list;

    protected TitheListViewAdapter(Context context, int resource, int textViewRId, ArrayList<String> items) {
        super(context, resource, textViewRId, items);
        this.list = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_tithe, null);
        }//end if
        TextView text1 = (TextView) convertView.findViewById(R.id.titleTextView);
        TextView text2 = (TextView) convertView.findViewById(R.id.amountTextView);
        StringTokenizer tokens = new StringTokenizer(list.get(position).toString(), ";");
        String temp = "";
        if (text1 != null && (temp = tokens.nextToken()) != null) {
            text1.setText(temp);
        }//end if
        else {
            text1.setText("");
        }//end else
        if (text2 != null && (temp = tokens.nextToken()) != null) {
            text2.setText(temp);
        }//end if
        else {
            text2.setText("");
        }//end else
        return convertView;
    }
}
