package com.uae.tra_smart_services.rest.model.response;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ak-buffalo on 21.10.15.
 */

public class GetAnnouncementsResponseModel {

    public class Announcements {
        @Expose public String _id;
        @Expose public String title;
        @Expose public String description;
        @Expose public String link;
        @Expose public String createdAt;
        @Expose public String pubDate;
        @Expose public String image;
    }
    @Expose public List<Announcements> announcements;
//    public static class List extends ArrayList<Announcements>{}
}

