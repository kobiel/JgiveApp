package com.jgive.kobieliasi.jgiveapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Kobi Eliasi on 05/06/2017.
 * Organizations List View Adapter
 */

public class OrganizationsListViewAdapter extends ArrayAdapter {
    private ArrayList<String> list;

    protected OrganizationsListViewAdapter(Context context, int resource, int textViewRId, ArrayList<String> items) {
        super(context, resource, textViewRId, items);
        this.list = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_organizations, null);
        }//end if
        ImageView image1 = (ImageView) convertView.findViewById(R.id.organizationImageView);
        TextView text1 = (TextView) convertView.findViewById(R.id.titleTextView);
        TextView text2 = (TextView) convertView.findViewById(R.id.organizationTextView);
        TextView text3 = (TextView) convertView.findViewById(R.id.detailsTextView);
        TextView text4 = (TextView) convertView.findViewById(R.id.barTextView);
        StringTokenizer tokens = new StringTokenizer(list.get(position).toString(), ";");
        String currentToken = "";
        if (image1 != null && (currentToken = tokens.nextToken()) != null) {
            new ImageDownloaderTask(image1).execute(currentToken);
        }//end if
        else {
            // Set the Jgive logo as default photo
            image1.setImageResource(R.drawable.jgive_logo);
        }//end else
        if (text1 != null && (currentToken = tokens.nextToken()) != null) {
            text1.setText(currentToken);
        }//end if
        else {
            // Set empty string as default
            text1.setText("");
        }//end else
        if (text2 != null && (currentToken = tokens.nextToken()) != null) {
            text2.setText(currentToken);
        }//end if
        else {
            // Set empty string as default
            text2.setText("");
        }//end else
        if (text3 != null && (currentToken = tokens.nextToken()) != null) {
            text3.setText(currentToken);
        }//end if
        else {
            // Set empty string as default
            text3.setText("");
        }//end else
        if (text4 != null && (currentToken = tokens.nextToken()) != null) {
            text4.setText(currentToken);
        }//end if
        else {
            // Set empty string as default
            text4.setText("");
        }//end else
        return convertView;
    }
}
