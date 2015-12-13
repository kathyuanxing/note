package com.example.testandroid;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by kathy on 2015/12/13.
 */
public class ExpressionAdapter extends ArrayAdapter<String> {

    public ExpressionAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(getContext(), R.layout.row_expression, null);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_expression);

        String filename = getItem(position);
        int resId = getContext().getResources().getIdentifier(filename, "drawable", getContext().getPackageName());
        imageView.setImageResource(resId);

        return convertView;
    }

}