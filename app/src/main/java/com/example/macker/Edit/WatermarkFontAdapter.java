package com.example.macker.Edit;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.example.macker.R;

public class WatermarkFontAdapter extends BaseAdapter {
    Context context;
    int lastClick = 0;

    int fontArray[];

    WatermarkFontAdapter(Context context, int fontArray[]) {
        this.context = context;
        this.fontArray = fontArray;
    }

    @Override
    public int getCount() {
        return fontArray.length + 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.watermark_font_flow, parent, false);
        }
        final TextView watermarkFlowTextView = convertView.findViewById(R.id.watermarkFlowTextView);
        watermarkFlowTextView.setText("Style " + position);
        if (position == 0) {
            watermarkFlowTextView.setText("Default");
            watermarkFlowTextView.setTypeface(Typeface.create("sans-serif-thin", Typeface.NORMAL));
        } else {
            Typeface typeface = ResourcesCompat.getFont(context, fontArray[position - 1]);
            watermarkFlowTextView.setTypeface(typeface);
        }

        return convertView;
    }
}
