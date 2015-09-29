package com.uae.tra_smart_services.rest.robo_requests;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import com.uae.tra_smart_services.rest.TRAServicesAPI;
import com.uae.tra_smart_services.rest.model.request.ComplainServiceProviderModel;
import com.uae.tra_smart_services.util.ImageUtils;

import java.io.IOException;

import retrofit.client.Response;

/**
 * Created by Mikazme on 13/08/2015.
 */
public class ComplainAboutServiceRequest extends BaseRequest<Response, TRAServicesAPI> {

    private final ComplainServiceProviderModel mComplainServiceModel;
    private final ContentResolver mContentResolver;
    private final Uri mImageUri;

    public ComplainAboutServiceRequest(final ComplainServiceProviderModel _complainServiceProviderModel,
                                       final Context _context,
                                       final Uri _imageUri) {

        super(Response.class, TRAServicesAPI.class);
        mComplainServiceModel = _complainServiceProviderModel;
        mContentResolver = _context.getApplicationContext().getContentResolver();
        mImageUri = _imageUri;
    }

    @Override
    public final Response loadDataFromNetwork() throws Exception {
        try {
            mComplainServiceModel.attachment = ImageUtils.imageToBase64(mContentResolver, mImageUri);
            return getService().complainServiceProvider(mComplainServiceModel);
        } catch (IOException e) {
            throw new Exception("Can't load image from device");
        }
    }

}
