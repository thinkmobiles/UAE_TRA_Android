package com.uae.tra_smart_services.rest.robo_requests;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.uae.tra_smart_services.rest.TRAServicesAPI;

import java.io.InputStream;

/**
 * Created by mobimaks on 08.10.2015.
 */
public class ImageFromUriRequest extends BaseRequest<Drawable, TRAServicesAPI> {

    private final Context mContext;
    private final Uri mUri;

    public ImageFromUriRequest(final @NonNull Context _context,
                               final @NonNull Uri _uri) {
        super(Drawable.class, TRAServicesAPI.class);
        mContext = _context;
        mUri = _uri;
    }

    @Override
    public Drawable loadDataFromNetwork() throws Exception {
        InputStream is = null;
        try {
            is = mContext.getContentResolver().openInputStream(mUri);
            return Drawable.createFromStream(is, mUri.toString());
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}
