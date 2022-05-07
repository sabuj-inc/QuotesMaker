package com.example.macker.Edit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.macker.R;

public class EditTextAdapter extends BaseAdapter {
    Context context;
    TextView messageEditText,authorEditText;
    String[] quoteArray;
    EditTextAdapter(Context context,TextView messageEditText, TextView authorEditText){
        this.context = context;
        this.messageEditText = messageEditText;
        this.authorEditText = authorEditText;
        quoteArray = context.getResources().getStringArray(R.array.quoteArray);
    }
    @Override
    public int getCount() {
        return quoteArray.length;
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
            convertView = inflater.inflate(R.layout.edit_text_list_flow, parent, false);
        }

        final TextView message = convertView.findViewById(R.id.message);
        final TextView author = convertView.findViewById(R.id.author);
        int start = quoteArray[position].indexOf("*");
        int end = quoteArray[position].length();
        message.setText(quoteArray[position].substring(0,start));
        author.setText(quoteArray[position].substring(start+1,end));
        LinearLayout editTextLayout = convertView.findViewById(R.id.editTextLayout);
        editTextLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageEditText.setText(message.getText().toString());
                authorEditText.setText(author.getText().toString());
            }
        });

        return convertView;
    }
}
