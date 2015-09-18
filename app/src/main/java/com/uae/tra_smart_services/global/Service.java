package com.uae.tra_smart_services.global;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.uae.tra_smart_services.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mobimaks on 10.08.2015.
 */
public enum Service {
    COMPLAIN_ABOUT_PROVIDER {
        @Override
        @StringRes
        public int getTitleRes() {
            return R.string.service_complain_about_provider;
        }

        @Override
        @DrawableRes
        public final int getDrawableRes() {
            return R.drawable.ic_edit;
        }

        @Override
        @StringRes
        public int getInfo_AboutService() {
            return R.string.str__service_info__about_service__complain_about_service_provider;
        }
    },
    COMPLAINT_ABOUT_TRA {
        @Override
        @StringRes
        public int getTitleRes() {
            return R.string.service_complain_about_tra;
        }

        @Override
        @DrawableRes
        public final int getDrawableRes() {
            return R.drawable.ic_chat;
        }

        @Override
        @StringRes
        public int getInfo_AboutService() {
            return R.string.str__service_info__about_service__default;
        }
    },
    SUGGESTION {
        @Override
        @StringRes
        public int getTitleRes() {
            return R.string.service_suggestion;
        }

        @Override
        @DrawableRes
        public final int getDrawableRes() {
            return R.drawable.ic_sugg;
        }

        @Override
        @StringRes
        public int getInfo_AboutService() {
            return R.string.str__service_info__about_service__suggestion;
        }
    },
    DOMAIN_CHECK {
        @Override
        @StringRes
        public int getTitleRes() {
            return R.string.service_domain_check;
        }

        @Override
        @DrawableRes
        public final int getDrawableRes() {
            return R.drawable.ic_glb;
        }

        @Override
        @StringRes
        public int getInfo_AboutService() {
            return R.string.str__service_info__about_service__default;
        }
    },
    DOMAIN_CHECK_INFO {
        @Override
        @StringRes
        public int getTitleRes() {
            return R.string.service_domain_check;
        }

        @Override
        @DrawableRes
        public final int getDrawableRes() {
            return R.drawable.ic_glb;
        }

        @Override
        @StringRes
        public int getInfo_AboutService() {
            return R.string.str__service_info__about_service__default;
        }

        @Override
        public String toString() {
            return "domain_info_check";
        }
    },
    DOMAIN_CHECK_AVAILABILITY {
        @Override
        @StringRes
        public int getTitleRes() {
            return R.string.service_domain_check;
        }

        @Override
        @DrawableRes
        public final int getDrawableRes() {
            return R.drawable.ic_glb;
        }

        @Override
        @StringRes
        public int getInfo_AboutService() {
            return R.string.str__service_info__about_service__default;
        }

        @Override
        public String toString() {
            return "domain_info_availabity";
        }
    },
    ENQUIRIES {
        @Override
        @StringRes
        public int getTitleRes() {
            return R.string.service_enquiries;
        }

        @Override
        @DrawableRes
        public final int getDrawableRes() {
            return R.drawable.ic_play;
        }

        @Override
        @StringRes
        public int getInfo_AboutService() {
            return R.string.str__service_info__about_service__default;
        }
    },
    SMS_SPAM {
        @Override
        @StringRes
        public int getTitleRes() {
            return R.string.service_sms_spam;
        }

        @Override
        @DrawableRes
        public final int getDrawableRes() {
            return R.drawable.ic_glb;
        }

        @Override
        @StringRes
        public int getInfo_AboutService() {
            return R.string.str__service_info__about_service__default;
        }
    },
    POOR_COVERAGE {
        @Override
        @StringRes
        public int getTitleRes() {
            return R.string.service_poor_coverage;
        }

        @Override
        @DrawableRes
        public final int getDrawableRes() {
            return R.drawable.ic_glb;
        }

        @Override
        @StringRes
        public int getInfo_AboutService() {
            return R.string.str__service_info__about_service__default;
        }
    },
    HELP_SALIM {
        @Override
        @StringRes
        public int getTitleRes() {
            return R.string.service_help_salim;
        }

        @Override
        @DrawableRes
        public final int getDrawableRes() {
            return R.drawable.ic_edit;
        }

        @Override
        @StringRes
        public int getInfo_AboutService() {
            return R.string.str__service_info__about_service__default;
        }
    },
    MOBILE_VERIFICATION {
        @Override
        @StringRes
        public int getTitleRes() {
            return R.string.service_mobile_verification;
        }

        @Override
        @DrawableRes
        public final int getDrawableRes() {
            return R.drawable.ic_glb;
        }

        @Override
        @StringRes
        public int getInfo_AboutService() {
            return R.string.str__service_info__about_service__default;
        }
    },
    APPROVED_DEVICES {
        @Override
        @StringRes
        public int getTitleRes() {
            return R.string.service_approved_devices;
        }

        @Override
        @DrawableRes
        public final int getDrawableRes() {
            return R.drawable.ic_lock;
        }

        @Override
        @StringRes
        public int getInfo_AboutService() {
            return R.string.str__service_info__about_service__default;
        }
    };

    @DrawableRes
    public abstract int getDrawableRes();

    @StringRes
    public abstract int getTitleRes();

    @StringRes
    public abstract int getInfo_AboutService();

    public final String getTitle(final Context _context) {
        return _context.getString(getTitleRes());
    }

    private static List<Service> UNIQUE_SERVICES;

    public static List<Service> getUniqueServices() {
        initUniqueServicesList();
        return new ArrayList<>(UNIQUE_SERVICES);
    }

    private static void initUniqueServicesList() {
        if (UNIQUE_SERVICES == null) {
            UNIQUE_SERVICES = new ArrayList<>(Arrays.asList(Service.values()));
            UNIQUE_SERVICES.remove(DOMAIN_CHECK_AVAILABILITY);
            UNIQUE_SERVICES.remove(DOMAIN_CHECK_INFO);
        }
    }

    public static int getUniqueServicesCount() {
        initUniqueServicesList();
        return UNIQUE_SERVICES.size();
    }

    public static List<Service> getSecondaryServices() {
        List<Service> services = new ArrayList<>();
        services.add(COMPLAIN_ABOUT_PROVIDER);
        services.add(COMPLAINT_ABOUT_TRA);
        services.add(SUGGESTION);
        services.add(DOMAIN_CHECK);
        services.add(ENQUIRIES);
        services.add(HELP_SALIM);
        services.add(APPROVED_DEVICES);
        return services;
    }
}