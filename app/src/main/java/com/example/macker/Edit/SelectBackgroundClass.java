package com.example.macker.Edit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.macker.R;

class SelectBackgroundClass extends BaseAdapter {
    Context context;
    int backgroundImage[];

    SelectBackgroundClass(Context context, int backgroundImage[]) {
        this.context = context;
        this.backgroundImage = backgroundImage;
    }

    @Override
    public int getCount() {
        return backgroundImage.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.background_flow_layout, parent, false);
        }
        ImageView imageView = convertView.findViewById(R.id.flowImageView);
        imageView.setImageResource(backgroundImage[position]);

        return convertView;
    }
}