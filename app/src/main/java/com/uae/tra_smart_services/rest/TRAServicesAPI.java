package com.uae.tra_smart_services.rest;

import com.uae.tra_smart_services.rest.model.request.ChangePasswordModel;
import com.uae.tra_smart_services.rest.model.request.ComplainServiceProviderModel;
import com.uae.tra_smart_services.rest.model.request.ComplainTRAServiceModel;
import com.uae.tra_smart_services.rest.model.request.HelpSalimModel;
import com.uae.tra_smart_services.rest.model.request.LoginModel;
import com.uae.tra_smart_services.rest.model.request.PoorCoverageRequestModel;
import com.uae.tra_smart_services.rest.model.request.RatingServiceRequestModel;
import com.uae.tra_smart_services.rest.model.request.RegisterModel;
import com.uae.tra_smart_services.rest.model.request.RestorePasswordRequestModel;
import com.uae.tra_smart_services.rest.model.request.SmsBlockRequestModel;
import com.uae.tra_smart_services.rest.model.request.SmsReportRequestModel;
import com.uae.tra_smart_services.rest.model.request.UserNameModel;
import com.uae.tra_smart_services.rest.model.response.DomainAvailabilityCheckResponseModel;
import com.uae.tra_smart_services.rest.model.response.DomainInfoCheckResponseModel;
import com.uae.tra_smart_services.rest.model.response.RatingServiceResponseModel;
import com.uae.tra_smart_services.rest.model.response.SearchDeviceResponseModel;
import com.uae.tra_smart_services.rest.model.response.ServiceInfoResponse;
import com.uae.tra_smart_services.rest.model.response.SmsSpamResponseModel;
import com.uae.tra_smart_services.rest.model.response.TransactionResponse;
import com.uae.tra_smart_services.rest.model.response.UserProfileResponseModel;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Query;

import static com.uae.tra_smart_services.global.ServerConstants.CHANGE_PASSWORD;
import static com.uae.tra_smart_services.global.ServerConstants.CHECK_WHO_IS_AVAILABLE_URL;
import static com.uae.tra_smart_services.global.ServerConstants.CHECK_WHO_IS_URL;
import static com.uae.tra_smart_services.global.ServerConstants.COMPLAIN_ABOUT_SERVICE_PROVIDER_URL;
import static com.uae.tra_smart_services.global.ServerConstants.COMPLAIN_ABOUT_TRA_SERVICE_URL;
import static com.uae.tra_smart_services.global.ServerConstants.COMPLAIN_ENQUIRIES_SERVICE_URL;
import static com.uae.tra_smart_services.global.ServerConstants.GET_TRANSACTIONS;
import static com.uae.tra_smart_services.global.ServerConstants.HELP_SALIM_URL;
import static com.uae.tra_smart_services.global.ServerConstants.LOGIN_URL;
import static com.uae.tra_smart_services.global.ServerConstants.LOGOUT_URL;
import static com.uae.tra_smart_services.global.ServerConstants.PARAMETER_CHECK_URL;
import static com.uae.tra_smart_services.global.ServerConstants.PARAMETER_DEVICE_BRAND;
import static com.uae.tra_smart_services.global.ServerConstants.PARAMETER_END_LIMIT;
import static com.uae.tra_smart_services.global.ServerConstants.PARAMETER_IMEI;
import static com.uae.tra_smart_services.global.ServerConstants.PARAMETER_LANGUAGE;
import static com.uae.tra_smart_services.global.ServerConstants.PARAMETER_SERVICE_NAME;
import static com.uae.tra_smart_services.global.ServerConstants.PARAMETER_START_OFFSET;
import static com.uae.tra_smart_services.global.ServerConstants.POOR_COVERAGE_URL;
import static com.uae.tra_smart_services.global.ServerConstants.RATING_SERVICE_URL;
import static com.uae.tra_smart_services.global.ServerConstants.REGISTER_URL;
import static com.uae.tra_smart_services.global.ServerConstants.RESTORE_PASS_URL;
import static com.uae.tra_smart_services.global.ServerConstants.SEARCH_DEVICE_BY_BRAND_NAME_URL;
import static com.uae.tra_smart_services.global.ServerConstants.SEARCH_DEVICE_BY_IMEI_URL;
import static com.uae.tra_smart_services.global.ServerConstants.SEND_SUGGESTION_URL;
import static com.uae.tra_smart_services.global.ServerConstants.SERVICE_INFO;
import static com.uae.tra_smart_services.global.ServerConstants.SMS_SPAM_BLOCK_URL;
import static com.uae.tra_smart_services.global.ServerConstants.SMS_SPAM_REPORT_URL;
import static com.uae.tra_smart_services.global.ServerConstants.USER_PROFILE;

/**
 * Created by Mikazme on 13/08/2015.
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
    RatingServiceResponseModel ratingService(@Body RatingServiceRequestModel _ratingServiceModel);

    @POST(SMS_SPAM_REPORT_URL)
    SmsSpamResponseModel reportSmsSpam(@Body SmsReportRequestModel _smsSpamModel);


    @POST(SMS_SPAM_BLOCK_URL)
    SmsSpamResponseModel blockSmsSpam(@Body SmsBlockRequestModel _smsSpamModel);

    @POST(POOR_COVERAGE_URL)
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

    @POST(REGISTER_URL)
    Response register(@Body RegisterModel _registerModel);

    @POST(LOGIN_URL)
    Response login(@Body LoginModel _loginModel);

    @POST(RESTORE_PASS_URL)
    Response restorePassword(@Body RestorePasswordRequestModel _loginModel);

    @POST(LOGOUT_URL)
    Response logout();

    @GET(USER_PROFILE)
    UserProfileResponseModel getUserProfile();

    @PUT(USER_PROFILE)
    UserProfileResponseModel editUserProfile(@Body UserNameModel _userName);

    @PUT(CHANGE_PASSWORD)
    Response changePassword(@Body ChangePasswordModel _changePasswordModel);

    @GET(GET_TRANSACTIONS)
    TransactionResponse.List getTransactions(@Query("page") final int _page,
                                             @Query("count") final int _count);
//                                             @Query("orderAsc") final int _asc);

    @GET(SERVICE_INFO)
    ServiceInfoResponse getServiceInfo(@Query(PARAMETER_SERVICE_NAME) String _serviceName,
                                       @Query(PARAMETER_LANGUAGE) String _language);

}
