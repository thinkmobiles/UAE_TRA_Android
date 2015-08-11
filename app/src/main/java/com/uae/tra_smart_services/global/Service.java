package com.uae.tra_smart_services.global;

/**
 * Created by mobimaks on 10.08.2015.
 */
public enum Service {
    DOMAIN_CHECK {
        @Override
        public String toString() {
            return "Domain Check service";
        }
    },
    COMPLAIN_ABOUT_PROVIDER {
        @Override
        public String toString() {
            return "Complain about service provider";
        }
    },
    COMPLAINT_ABOUT_TRA {
        @Override
        public String toString() {
            return "Complain about TRA";
        }
    },
    ENQUIRIES {
        @Override
        public String toString() {
            return "Enquiries";
        }
    },
    SUGGESTION {
        @Override
        public String toString() {
            return "Suggestion";
        }
    },
    SMS_SPAM {
        @Override
        public String toString() {
            return "SMS Spam";
        }
    },
    POOR_COVERAGE {
        @Override
        public String toString() {
            return "Poor coverage";
        }
    },
    HELP_SALIM {
        @Override
        public String toString() {
            return "Help Salim";
        }
    },
    MOBILE_VERIFICATION {
        @Override
        public String toString() {
            return "Mobile verification";
        }
    },
    APPROVED_DEVICES {
        @Override
        public String toString() {
            return "Approved devices";
        }
    }
}