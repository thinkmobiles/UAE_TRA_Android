package com.uae.tra_smart_services.rest.new_request;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import com.uae.tra_smart_services.rest.TRAServicesAPI;
import com.uae.tra_smart_services.rest.model.new_request.ComplainTRAServiceModel;
import com.uae.tra_smart_services.util.BitmapUtils;

import java.io.IOException;

import retrofit.client.Response;

/**
 * Created by Vitaliy on 13/08/2015.
 */
public class ComplainEnquiriesServiceRequest extends BaseRequest<Response, TRAServicesAPI> {

    private final ComplainTRAServiceModel mComplainTRAServiceModel;
    private final ContentResolver mContentResolver;
    private final Uri mImageUri;

    public ComplainEnquiriesServiceRequest(final ComplainTRAServiceModel _complainTRAServiceModel,
                                           final Context _context,
                                           final Uri _imageUri) {

        super(Response.class, TRAServicesAPI.class);
        mComplainTRAServiceModel = _complainTRAServiceModel;
        mContentResolver = _context.getApplicationContext().getContentResolver();
        mImageUri = _imageUri;
    }

    @Override
    public Response loadDataFromNetwork() throws Exception {
        try {
            mComplainTRAServiceModel.attachment = BitmapUtils.imageToBase64(mContentResolver, mImageUri);
            return getService().complainEnquiries(mComplainTRAServiceModel);
        } catch (IOException e) {
            throw new Exception("Can't load image from device");
        }
    }
}