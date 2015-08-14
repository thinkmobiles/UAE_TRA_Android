package com.uae.tra_smart_services.global;

/**
 * Created by mobimaks on 30.07.2015.
 */
public final class ServerConstants {

    private ServerConstants() {
    }

    public static final String BASE_URL = "http://134.249.164.53:7791";
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


    //!!!!!!!!!!!!! NEW API !!!!!!!!!!!!!!!!!!!!!!!!
    public static final String CHECK_WHO_IS_URL = "/checkWhois";
    public static final String CHECK_WHO_IS_AVAILABLE_URL = "/checkWhoisAvailable";
    public static final String SEARCH_DEVICE_BY_IMEI_URL = "/searchMobile";
    public static final String SEARCH_DEVICE_BY_BRAND_NAME_URL = "/searchMobileBrand";
    public static final String NEW_RATING_SERVICE_URL = "/feedback";
    public static final String SMS_SPAM_REPORT_URL = "/complainSmsSpam";
    public static final String HELP_SALIM_URL = "/sendHelpSalim";
    public static final String COMPLAIN_ABOUT_SERVICE_PROVIDER_URL = "/complainServiceProvider";
    public static final String COMPLAIN_ABOUT_TRA_SERVICE_URL = "/complainTRAService";

    //!!!!!!!!!!!! API PARAMETERS !!!!!!!!!!!!!!!!!!
    public static final String PARAMETER_CHECK_URL = "checkUrl";
    public static final String PARAMETER_IMEI = "imei";
    public static final String PARAMETER_DEVICE_BRAND = "brand";
    public static final String PARAMETER_START_OFFSET = "start";
    public static final String PARAMETER_END_LIMIT = "end";

    //!!!!!!!!!!!! SERVER RESPONSES !!!!!!!!!!!!!!!!!
    public static final String AVAILABLE = "Available";
    public static final String NOT_AVAILABLE = "Not Available";
}
