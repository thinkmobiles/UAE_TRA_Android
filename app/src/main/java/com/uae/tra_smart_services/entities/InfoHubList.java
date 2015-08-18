package com.uae.tra_smart_services.entities;

/**
 * Created by ak-buffalo on 18.08.15.
 */
public enum InfoHubList {
    ONE(
            "http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg",
            "Dummy description",
            "12.08.2015, 20:12"
    ),
    TWO(
            "http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg",
                    "Dummy description",
                    "12.08.2015, 20:12"
    ),
    THREE(
            "http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg",
                    "Dummy description",
                    "12.08.2015, 20:12"
    ),
    FOUR(
            "http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg",
                    "Dummy description",
                    "12.08.2015, 20:12"
    );

    String iconUrl, descr, date;
    InfoHubList(String _iconUrl, String _descr, String _date){
        iconUrl = _iconUrl;
        descr = _descr;
        date = _date;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getDescr() {
        return descr;
    }

    public String getDate() {
        return date;
    }
}
