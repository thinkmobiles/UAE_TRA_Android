package com.uae.tra_smart_services.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uae.tra_smart_services.global.AttachmentOption;

/**
 * Created by mobimaks on 06.10.2015.
 */
public class AttachmentOptionsAdapter extends ArrayAdapter<AttachmentOption> {

    public AttachmentOptionsAdapter(Context context, AttachmentOption[] _options) {
        super(context, android.R.layout.simple_list_item_1, _options);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TextView textView = (TextView) super.getView(position, convertView, parent);
        final AttachmentOption option = getItem(position);

        if (option == AttachmentOption.DELETE_ATTACHMENT) {
            textView.setTextColor(Color.RED);
        } else {
            textView.setTextColor(Color.BLACK);
        }
        textView.setText(option.getTitleRes());
        return textView;
    }
}