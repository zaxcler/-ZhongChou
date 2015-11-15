package com.lianhai.zhongchou.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.lianhai.zhongchou.R;

import java.util.List;

import static android.view.View.inflate;

/**
 * Created by zaxcler on 15/10/15.
 */
public class SpinnerAdapter extends ArrayAdapter<String>{
    private List<String> list;
    private Spinner currentSpinner;
    private Context context;


    public SpinnerAdapter(Context context, int resource, List<String> objects,Spinner spinner) {
        super(context, resource, objects);
        list=objects;
        currentSpinner=spinner;
        this.context=context;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        View view = inflate(getContext(), R.layout.spinner_item_layout,
                null);
        TextView label = (TextView) view
                .findViewById(R.id.spinner_item_text);
        ImageView check = (ImageView) view
                .findViewById(R.id.spinner_item_image);
        label.setText(list.get(position));
        if (currentSpinner.getSelectedItemPosition() == position) {
            check.setImageResource(R.drawable.red_dian);
        } else {
            check.setImageResource(R.drawable.grey_dian);
        }

        return view;
    }
}
