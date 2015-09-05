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
    };

    @DrawableRes
    public abstract int getDrawableRes();

    @StringRes
    public abstract int getTitleRes();

    public final String getTitle(final Context _context) {
        return _context.getString(getTitleRes());
    }

    private static List<Service> ALL_SERVICES;

    public static List<Service> getAllServices() {
        initAllServicesList();
        return new ArrayList<>(ALL_SERVICES);
    }

    private static void initAllServicesList() {
        if (ALL_SERVICES == null) {
            ALL_SERVICES = new ArrayList<>(Arrays.asList(Service.values()));
        }
    }

    public static int getAllServicesCount() {
        initAllServicesList();
        return ALL_SERVICES.size();
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