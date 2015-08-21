package com.uae.tra_smart_services.rest.model.response;

import android.os.Parcel;

/**
 * Created by ak-buffalo on 20.08.15.
 */
public class InfoHubListItemModel<T> {
    private String mIconUrl;
    private String mHeaderImageUrl;
    private String mTitle = "";
    private String mDescription;
    private String mFullDescription;
    private String mDate;

    protected InfoHubListItemModel(){}

    public T setDate(String mDate) {
        this.mDate = mDate;
        return (T) this;
    }

    public T setIconUrl(String mIconUrl) {
        this.mIconUrl = mIconUrl;
        return (T) this;
    }

    public String getHeaderImageUrl() {
        return mHeaderImageUrl;
    }

    public T setHeaderImageUrl(String _headerImageUrl) {
        this.mHeaderImageUrl = _headerImageUrl;
        return (T) this;
    }

    public T setTitle(String title) {
        mTitle = title;
        return (T) this;
    }

    public T setDescription(String mDescription) {
        this.mDescription = mDescription;
        return (T) this;
    }

    public T setFullDescription(String _fullDescription) {
        this.mFullDescription = _fullDescription;
        return (T) this;
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

    public String getFullDescription() {
        return mFullDescription;
    }

    public String getDate() {
        return mDate;
    }

    @Override
    public String toString() {
        return getDescription();
    }

    public int describeContents() {
        return 0;
    }

    public int mData;
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mData);
    }


    public InfoHubListItemModel(Parcel in) {
        mData = in.readInt();
    }
}
