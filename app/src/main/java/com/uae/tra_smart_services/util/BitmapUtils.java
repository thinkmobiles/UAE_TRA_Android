package com.uae.tra_smart_services.util;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by mobimaks on 14.08.2015.
 */
public final class BitmapUtils {

    private static final String BASE64_PNG_HEADER = "data:image/png;base64,";

    private BitmapUtils() {
    }

    @Nullable
    public static String imageToBase64(final ContentResolver _resolver, final Uri _uri) throws IOException {
        if (_resolver == null || _uri == null) {
            return null;
        }
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(_resolver, _uri);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        boolean isCompressed = bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream);
        if (isCompressed) {
            byte[] ba = byteStream.toByteArray();
            return BASE64_PNG_HEADER + Base64.encodeToString(ba, Base64.DEFAULT);
        }
        return null;
    }
}
