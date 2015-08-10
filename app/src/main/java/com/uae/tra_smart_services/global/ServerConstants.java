package com.uae.tra_smart_services.global;

/**
 * Created by mobimaks on 30.07.2015.
 */
public final class ServerConstants {

    private ServerConstants() {
    }

    public static final String BASE_URL = "http://tma.tra.gov.ae/tra_api/service";
    public static final String AUTH_URL = "/auth";
    public static final String ACCESS_TOKEN = "access_token";
    //Domain check service
    public static final String DNS_LOOKUP_URL = "/dnslookup";

    //Approved devices
    public static final String BREND_NAME = "brendName";
    public static final String SEARCH_MODEL = "searchModelName";
    public static final String BASE_APPROVED_DEVICES_URL = "/getdevicebrandmodel/%1$s/%2$s";
    public static final String APPROVED_DEVICES_URL = BASE_APPROVED_DEVICES_URL + "/{" + BREND_NAME + "}/" + SEARCH_MODEL;
    public static final String ALL_APPROVED_DEVICES_URL = BASE_APPROVED_DEVICES_URL + "/all" + "/{" + SEARCH_MODEL + "}";

    //Check mobile verification
    public static final String IS_FAKE_DEVICE_URL = "/isfakedevice";

    //Report poor coverage
    public static final String SIGNAL_COVERAGE_URL = "/signalcoverage";

    //Help Salim
    public static final String BLOCK_WEBSITE_URL = "/blockwebsite";

    //Rating
    public static final String RATING_SERVICE_URL = "/servicerating";

    //SMS spam
    public static final String SMS_SPAM_URL = "/insertsmsspam";

    //Search
    public static final String SEARCH_STRING = "searchString";
    public static final String SEARCH_URL = "/taggedsearch/{" + SEARCH_STRING + "}";
}
