package com.uae.tra_smart_services.global;

import android.support.annotation.DrawableRes;

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
        public String toString() {
            return "Complain About Service Provider";
        }

        @Override
        @DrawableRes
        public final int getDrawableRes() {
            return R.drawable.ic_edit;
        }
    },
    COMPLAINT_ABOUT_TRA {
        @Override
        public String toString() {
            return "Complain About TRA";
        }

        @Override
        @DrawableRes
        public final int getDrawableRes() {
            return R.drawable.ic_chat;
        }
    },
    SUGGESTION {
        @Override
        public String toString() {
            return "Suggestion";
        }

        @Override
        @DrawableRes
        public final int getDrawableRes() {
            return R.drawable.ic_sugg;
        }
    },
    DOMAIN_CHECK {
        @Override
        public String toString() {
            return "Domain Check Service";
        }

        @Override
        @DrawableRes
        public final int getDrawableRes() {
            return R.drawable.ic_glb;
        }
    },
    ENQUIRIES {
        @Override
        public String toString() {
            return "Enquiries";
        }

        @Override
        @DrawableRes
        public final int getDrawableRes() {
            return R.drawable.ic_play;
        }
    },
    SMS_SPAM {
        @Override
        public String toString() {
            return "SMS Spam Service";
        }

        @Override
        @DrawableRes
        public final int getDrawableRes() {
            return R.drawable.ic_glb;
        }
    },
    POOR_COVERAGE {
        @Override
        public String toString() {
            return "Poor coverage";
        }

        @Override
        @DrawableRes
        public final int getDrawableRes() {
            return R.drawable.ic_glb;
        }
    },
    HELP_SALIM {
        @Override
        public String toString() {
            return "Help Salim";
        }

        @Override
        @DrawableRes
        public final int getDrawableRes() {
            return R.drawable.ic_edit;
        }
    },
    MOBILE_VERIFICATION {
        @Override
        public String toString() {
            return "Mobile verification";
        }

        @Override
        @DrawableRes
        public final int getDrawableRes() {
            return R.drawable.ic_glb;
        }
    },
    APPROVED_DEVICES {
        @Override
        public String toString() {
            return "Approved Devices";
        }

        @Override
        @DrawableRes
        public final int getDrawableRes() {
            return R.drawable.ic_lock;
        }
    };

    @DrawableRes
    public abstract int getDrawableRes();

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