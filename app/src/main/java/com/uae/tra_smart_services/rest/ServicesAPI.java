package com.uae.tra_smart_services.rest;

import com.uae.tra_smart_services.rest.model.request.CoverageRequest;
import com.uae.tra_smart_services.rest.model.request.DnsRequest;
import com.uae.tra_smart_services.rest.model.request.ImeiCode;
import com.uae.tra_smart_services.rest.model.request.Rating;
import com.uae.tra_smart_services.rest.model.request.SmsSpamReport;
import com.uae.tra_smart_services.rest.model.request.WebUrl;
import com.uae.tra_smart_services.rest.model.responce.AccessToken;
import com.uae.tra_smart_services.rest.model.responce.ApprovedDevices;
import com.uae.tra_smart_services.rest.model.responce.DeviceStatus;
import com.uae.tra_smart_services.rest.model.responce.SearchResults;
import com.uae.tra_smart_services.rest.model.responce.Status;
import com.uae.tra_smart_services.rest.model.responce.UserData;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

import static com.uae.tra_smart_services.global.ServerConstants.ACCESS_TOKEN;
import static com.uae.tra_smart_services.global.ServerConstants.ALL_APPROVED_DEVICES_URL;
import static com.uae.tra_smart_services.global.ServerConstants.APPROVED_DEVICES_URL;
import static com.uae.tra_smart_services.global.ServerConstants.AUTH_URL;
import static com.uae.tra_smart_services.global.ServerConstants.BLOCK_WEBSITE_URL;
import static com.uae.tra_smart_services.global.ServerConstants.BREND_NAME;
import static com.uae.tra_smart_services.global.ServerConstants.DNS_LOOKUP_URL;
import static com.uae.tra_smart_services.global.ServerConstants.IS_FAKE_DEVICE_URL;
import static com.uae.tra_smart_services.global.ServerConstants.RATING_SERVICE_URL;
import static com.uae.tra_smart_services.global.ServerConstants.SEARCH_MODEL;
import static com.uae.tra_smart_services.global.ServerConstants.SEARCH_STRING;
import static com.uae.tra_smart_services.global.ServerConstants.SEARCH_URL;
import static com.uae.tra_smart_services.global.ServerConstants.SIGNAL_COVERAGE_URL;
import static com.uae.tra_smart_services.global.ServerConstants.SMS_SPAM_URL;

/**
 * Created by mobimaks on 30.07.2015.
 */
public interface ServicesAPI {

    String JSON_TYPE = "Content-Type: application/json";

    @Headers(JSON_TYPE)
    @POST(AUTH_URL)
    AccessToken authenticate(@Body ImeiCode imeiCode);

    @Headers(JSON_TYPE)
    @POST(DNS_LOOKUP_URL)
    UserData getDnsUserData(@Query(ACCESS_TOKEN) String token,
                            @Body DnsRequest dnsRequest);

    @Headers(JSON_TYPE)
    @GET(APPROVED_DEVICES_URL)
    ApprovedDevices getApprovedDevices(@Query(ACCESS_TOKEN) String token,
                                       @Path(BREND_NAME) String brend,
                                       @Path(SEARCH_MODEL) String model);

    @Headers(JSON_TYPE)
    @GET(ALL_APPROVED_DEVICES_URL)
    ApprovedDevices getAllApprovedDevices(@Query(ACCESS_TOKEN) String token);

    @Headers(JSON_TYPE)
    @POST(IS_FAKE_DEVICE_URL)
    DeviceStatus getDeviceStatus(@Body ImeiCode imeiCode);

    @Headers(JSON_TYPE)
    @POST(SIGNAL_COVERAGE_URL)
    Status reportPoorCoverage(@Query(ACCESS_TOKEN) String token,
                              @Body CoverageRequest coverageRequest);

    @Headers(JSON_TYPE)
    @POST(BLOCK_WEBSITE_URL)
    Status blockWebSite(@Query(ACCESS_TOKEN) String token,
                        @Body WebUrl url);

    @Headers(JSON_TYPE)
    @POST(RATING_SERVICE_URL)
    Status rateService(@Query(ACCESS_TOKEN) String token,
                       @Body Rating rating);

    @Headers(JSON_TYPE)
    @POST(SMS_SPAM_URL)
    Status reportSmsSpam(@Query(ACCESS_TOKEN) String token,
                         @Body SmsSpamReport spamReport);

    @Headers(JSON_TYPE)
    @GET(SEARCH_URL)
    SearchResults search(@Query(ACCESS_TOKEN) String token,
                         @Path(SEARCH_STRING) String searchQuery);

}
