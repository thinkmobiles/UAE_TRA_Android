package com.uae.tra_smart_services.global;

import android.support.annotation.DrawableRes;

import com.uae.tra_smart_services.R;

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
//    SMS_SPAM {
//        @Override
//        public String toString() {
//            return "SMS Spam";
//        }
//
//        @Override
//        @DrawableRes
//        public final int getDrawableRes() {
//            return R.drawable.ic_glb;
//        }
//    },
//    POOR_COVERAGE {
//        @Override
//        public String toString() {
//            return "Poor coverage";
//        }
//
//        @Override
//        @DrawableRes
//        public final int getDrawableRes() {
//            return R.drawable.ic_glb;
//        }
//    },
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
//    MOBILE_VERIFICATION {
//        @Override
//        public String toString() {
//            return "Mobile verification";
//        }
//
//        @Override
//        @DrawableRes
//        public final int getDrawableRes() {
//            return R.drawable.ic_glb;
//        }
//    },
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
}