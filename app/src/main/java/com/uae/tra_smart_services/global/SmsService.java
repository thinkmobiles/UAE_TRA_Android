package com.uae.tra_smart_services.global;

/**
 * Created by ak-buffalo on 11.08.15.
 */
public enum SmsService {
    REPORT {
        @Override
        public String toString() {
            return "Report Number";
        }
    },
    BLOCK {
        @Override
        public String toString() {
            return "Block Number";
        }
    }
}
