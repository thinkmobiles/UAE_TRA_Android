package com.uae.tra_smart_services.rest;

import com.squareup.picasso.Downloader;
import com.uae.tra_smart_services.rest.model.new_request.ComplainServiceProviderModel;
import com.uae.tra_smart_services.rest.model.new_request.ComplainTRAServiceModel;
import com.uae.tra_smart_services.rest.model.new_request.HelpSalimModel;
import com.uae.tra_smart_services.rest.model.new_request.PoorCoverageRequestModel;
import com.uae.tra_smart_services.rest.model.new_request.RatingServiceModel;
import com.uae.tra_smart_services.rest.model.new_request.SmsSpamRequestModel;
import com.uae.tra_smart_services.rest.model.new_response.DomainAvailabilityCheckResponseModel;
import com.uae.tra_smart_services.rest.model.new_response.DomainInfoCheckResponseModel;
import com.uae.tra_smart_services.rest.model.new_response.SearchDeviceResponseModel;
import com.uae.tra_smart_services.rest.model.new_response.SmsSpamResponseModel;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;

import static com.uae.tra_smart_services.global.ServerConstants.CHECK_WHO_IS_AVAILABLE_URL;
import static com.uae.tra_smart_services.global.ServerConstants.CHECK_WHO_IS_URL;
import static com.uae.tra_smart_services.global.ServerConstants.COMPLAIN_ABOUT_SERVICE_PROVIDER_URL;
import static com.uae.tra_smart_services.global.ServerConstants.COMPLAIN_ABOUT_TRA_SERVICE_URL;
import static com.uae.tra_smart_services.global.ServerConstants.COMPLAIN_ENQUIRIES_SERVICE_URL;
import static com.uae.tra_smart_services.global.ServerConstants.HELP_SALIM_URL;
import static com.uae.tra_smart_services.global.ServerConstants.PARAMETER_CHECK_URL;
import static com.uae.tra_smart_services.global.ServerConstants.PARAMETER_DEVICE_BRAND;
import static com.uae.tra_smart_services.global.ServerConstants.PARAMETER_END_LIMIT;
import static com.uae.tra_smart_services.global.ServerConstants.PARAMETER_IMEI;
import static com.uae.tra_smart_services.global.ServerConstants.PARAMETER_START_OFFSET;
import static com.uae.tra_smart_services.global.ServerConstants.RATING_SERVICE_URL;
import static com.uae.tra_smart_services.global.ServerConstants.SEARCH_DEVICE_BY_BRAND_NAME_URL;
import static com.uae.tra_smart_services.global.ServerConstants.SEARCH_DEVICE_BY_IMEI_URL;
import static com.uae.tra_smart_services.global.ServerConstants.SEND_SUGGESTION_URL;
import static com.uae.tra_smart_services.global.ServerConstants.SMS_SPAM_REPORT_URL;

/**
 * Created by Vitaliy on 13/08/2015.
 */
public interface TRAServicesAPI {

    String JSON_TYPE = "Content-Type: application/json";

    @GET(CHECK_WHO_IS_URL)
    DomainInfoCheckResponseModel getDomainData(@Query(PARAMETER_CHECK_URL) String _checkUrl);

    @GET(CHECK_WHO_IS_AVAILABLE_URL)
    DomainAvailabilityCheckResponseModel checkDomainAvailability(@Query(PARAMETER_CHECK_URL) String _checkUrl);

    @GET(SEARCH_DEVICE_BY_IMEI_URL)
    SearchDeviceResponseModel.List searchDeviceByImei(@Query(PARAMETER_IMEI) String _imei);

    @GET(SEARCH_DEVICE_BY_BRAND_NAME_URL)
    SearchDeviceResponseModel.List searchDeviceByBrandName(@Query(PARAMETER_DEVICE_BRAND) String _brand,
                                                 @Query(PARAMETER_START_OFFSET) Integer _start,
                                                 @Query(PARAMETER_END_LIMIT) Integer _end);

    @POST(RATING_SERVICE_URL)
    Response ratingService(@Body RatingServiceModel _ratingServiceModel);

    @POST(SMS_SPAM_REPORT_URL)
    SmsSpamResponseModel reportSmsSpam(@Body SmsSpamRequestModel _smsSpamModel);

    @POST(SMS_SPAM_REPORT_URL)
    Response poorCoverage(@Body PoorCoverageRequestModel _smsSpamModel);

    @POST(HELP_SALIM_URL)
    Response sendHelpSalim(@Body HelpSalimModel _helpSalimModel);

    @POST(COMPLAIN_ABOUT_SERVICE_PROVIDER_URL)
    Response complainServiceProvider(@Body ComplainServiceProviderModel _complainServiceProviderModel);

    @POST(COMPLAIN_ABOUT_TRA_SERVICE_URL)
    Response complainTraServiceProvider(@Body ComplainTRAServiceModel _complainTRAServiceModel);

    @Headers(JSON_TYPE)
    @POST(COMPLAIN_ENQUIRIES_SERVICE_URL)
    Response complainEnquiries(@Body ComplainTRAServiceModel _complainTRAServiceModel);

    @POST(SEND_SUGGESTION_URL)
    Response sendSuggestion(@Body ComplainTRAServiceModel _complainTRAServiceModel);
}
