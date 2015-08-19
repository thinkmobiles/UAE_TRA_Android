package com.uae.tra_smart_services.rest.model.new_response;

/**
 * Created by ak-buffalo on 18.08.15.
 */
public class InfoHubAnnouncementsListItemModel {
    private String mIconUrl;
    private String mDescription;
    private String mDate;

    public InfoHubAnnouncementsListItemModel setDate(String mDate) {
        this.mDate = mDate;
        return this;
    }

    public InfoHubAnnouncementsListItemModel setIconUrl(String mIconUrl) {
        this.mIconUrl = mIconUrl;
        return this;
    }

    public InfoHubAnnouncementsListItemModel setDescription(String mDescription) {
        this.mDescription = mDescription;
        return this;
    }


    public String getIconUrl() {
        return mIconUrl;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getDate() {
        return mDate;
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
