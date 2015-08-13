package com.uae.tra_smart_services.rest;

import com.uae.tra_smart_services.rest.model.new_request.ComplainServiceProviderModel;
import com.uae.tra_smart_services.rest.model.new_request.ComplainTRAServiceModel;
import com.uae.tra_smart_services.rest.model.new_request.HelpSalimModel;
import com.uae.tra_smart_services.rest.model.new_request.RatingServiceModel;
import com.uae.tra_smart_services.rest.model.new_request.SmsSpamModel;
import com.uae.tra_smart_services.rest.model.new_response.SearchDeviceResponse;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

import static com.uae.tra_smart_services.global.ServerConstants.*;

/**
 * Created by Vitaliy on 13/08/2015.
 */
public interface TRAServicesAPI {

    @GET(CHECK_WHO_IS_URL)
    String getDomainData(@Query(PARAMETER_CHECK_URL) String _checkUrl);

    @GET(CHECK_WHO_IS_AVAILABLE_URL)
    String checkDomainAvailability(@Query(PARAMETER_CHECK_URL) String _checkUrl);

    @GET(SEARCH_DEVICE_BY_IMEI_URL)
    SearchDeviceResponse.List searchDeviceByImei(@Query(PARAMETER_IMEI) String _imei);

    @GET(SEARCH_DEVICE_BY_BRAND_NAME_URL)
    SearchDeviceResponse.List searchDeviceByBrandName(@Query(PARAMETER_DEVICE_BRAND) String _brand,
                                                 @Query(PARAMETER_START_OFFSET) Integer _start,
                                                 @Query(PARAMETER_END_LIMIT) Integer _end);

    @POST(RATING_SERVICE_URL)
    Response ratingService(@Body RatingServiceModel _ratingServiceModel);

    @POST(SMS_SPAM_REPORT_URL)
    Response reportSmsSpam(@Body SmsSpamModel _smsSpamModel);

    @POST(HELP_SALIM_URL)
    Response sendHelpSalim(@Body HelpSalimModel _helpSalimModel);

    @POST(COMPLAIN_ABOUT_SERVICE_PROVIDER_URL)
    Response complainServiceProvider(@Body ComplainServiceProviderModel _complainServiceProviderModel);

    @POST(COMPLAIN_ABOUT_TRA_SERVICE_URL)
    Response complainTraServiceProvider(@Body ComplainTRAServiceModel _complainTRAServiceModel);
}
