package com.uae.tra_smart_services.entities;

import android.content.Context;
import android.widget.ImageButton;

import com.uae.tra_smart_services.entities.AbstractViewFactory;
import com.uae.tra_smart_services.entities.ThemeSelector;

/**
 * Created by ak-buffalo on 27.07.15.
 */
public class ImageButtonFactory extends AbstractViewFactory<ImageButton, ThemeSelector>{
    protected ImageButtonFactory(Context context) {
        super(context);
    }

    @Override
    public ImageButton createView(ThemeSelector data) {
        ImageButton button = new ImageButton(context);
        prepareData(button, data);
        return null;
    }

    @Override
    public void prepareData(ImageButton view, ThemeSelector data) {
        view.setImageDrawable(data.getImage());
    }
}
