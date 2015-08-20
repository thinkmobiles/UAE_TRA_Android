package com.uae.tra_smart_services.rest.model.new_response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ak-buffalo on 18.08.15.
 */
public class InfoHubListItemModel implements Parcelable{
    private String mIconUrl;
    private String mHeaderImageUrl;
    private String mTitle = "";
    private String mDescription;
    private String mFullDescription;
    private String mDate;

    public InfoHubListItemModel(){}

    public InfoHubListItemModel setDate(String mDate) {
        this.mDate = mDate;
        return this;
    }

    public InfoHubListItemModel setIconUrl(String mIconUrl) {
        this.mIconUrl = mIconUrl;
        return this;
    }

    public String getHeaderImageUrl() {
        return mHeaderImageUrl;
    }

    public InfoHubListItemModel setHeaderImageUrl(String _headerImageUrl) {
        this.mHeaderImageUrl = _headerImageUrl;
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

    public InfoHubListItemModel setFullDescription(String _fullDescription) {
        this.mFullDescription = _fullDescription;
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

    private int mData;
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mData);
    }

    public static final Parcelable.Creator<InfoHubListItemModel> CREATOR
            = new Parcelable.Creator<InfoHubListItemModel>() {
        public InfoHubListItemModel createFromParcel(Parcel in) {
            return new InfoHubListItemModel(in);
        }

        public InfoHubListItemModel[] newArray(int size) {
            return new InfoHubListItemModel[size];
        }
    };

    private InfoHubListItemModel(Parcel in) {
        mData = in.readInt();
    }
}
