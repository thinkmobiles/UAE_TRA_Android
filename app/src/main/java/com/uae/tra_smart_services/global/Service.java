package com.uae.tra_smart_services.global;

import android.support.annotation.DrawableRes;

import com.uae.tra_smart_services.R;

/**
 * Created by mobimaks on 10.08.2015.
 */
public enum Service {
    DOMAIN_CHECK {
        @Override
        public String toString() {
            return "Domain Check service";
        }

        @Override
        @DrawableRes
        public final int getDrawableRes() {
            return R.drawable.ic_glb;
        }
    },
    COMPLAIN_ABOUT_PROVIDER {
        @Override
        public String toString() {
            return "Complain about service provider";
        }

        @Override
        @DrawableRes
        public final int getDrawableRes() {
            return R.drawable.ic_glb;
        }
    },
    COMPLAINT_ABOUT_TRA {
        @Override
        public String toString() {
            return "Complain about TRA";
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
            return R.drawable.ic_glb;
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
            return R.drawable.ic_glb;
        }
    },
    SMS_SPAM {
        @Override
        public String toString() {
            return "SMS Spam";
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
            return R.drawable.ic_glb;
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
            return "Approved devices";
        }

        @Override
        @DrawableRes
        public final int getDrawableRes() {
            return R.drawable.ic_glb;
        }
    };

    @DrawableRes
    public abstract int getDrawableRes();
}