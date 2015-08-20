package com.uae.tra_smart_services.rest.model.new_response;

/**
 * Created by ak-buffalo on 18.08.15.
 */
public class InfoHubListItemModel {
    private String mIconUrl;
    private String mTitle = "";
    private String mDescription;
    private String mDate;

    public InfoHubListItemModel setDate(String mDate) {
        this.mDate = mDate;
        return this;
    }

    public InfoHubListItemModel setIconUrl(String mIconUrl) {
        this.mIconUrl = mIconUrl;
        return this;
    }
    public InfoHubListItemModel setTitle(String title) {
        mTitle = title;
        return this;
    }

    public InfoHubListItemModel setDescription(String mDescription) {
        this.mDescription = mDescription;
        return this;
    }

    public String getIconUrl() {
        return mIconUrl;
    }

    public String getTitle() {
        return mTitle;
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
