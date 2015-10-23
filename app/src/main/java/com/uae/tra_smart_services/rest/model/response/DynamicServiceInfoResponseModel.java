package com.uae.tra_smart_services.rest.model.response;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.uae.tra_smart_services.global.ServerConstants;

import java.util.ArrayList;

/**
 * Created by mobimaks on 20.10.2015.
 */
public final class DynamicServiceInfoResponseModel implements Parcelable {

    @Expose
    @SerializedName("_id")
    public String id;

    @Expose
    public String icon;

    private String iconUrl;

    public DynamicServiceInfoResponseModel() {
    }

    public String getIconUrl(final Context _context) {
        if (iconUrl == null) {
            String url = ServerConstants.BASE_URL;
            if (TextUtils.isEmpty(icon)) {
                iconUrl = url;
            } else {
                iconUrl = url + icon + "/" + getDensityClass(_context);
            }
        }
        return iconUrl;
    }

    private String getDensityClass(final Context _context) {
        final int densityDpi = _context.getResources().getDisplayMetrics().densityDpi;
        if (densityDpi <= DisplayMetrics.DENSITY_MEDIUM) {
            return "mdpi";
        } else if (densityDpi <= DisplayMetrics.DENSITY_HIGH) {
            return "hdpi";
        } else if (densityDpi <= DisplayMetrics.DENSITY_XHIGH) {
            return "xhdpi";
        } else if (densityDpi <= DisplayMetrics.DENSITY_XXHIGH) {
            return "xxhdpi";
        } else {
            return "xxxhdpi";
        }
    }

    public static class List extends ArrayList<DynamicServiceInfoResponseModel> {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.icon);
        dest.writeString(this.iconUrl);
    }

    protected DynamicServiceInfoResponseModel(Parcel in) {
        this.id = in.readString();
        this.icon = in.readString();
        this.iconUrl = in.readString();
    }

    public static final Parcelable.Creator<DynamicServiceInfoResponseModel> CREATOR = new Parcelable.Creator<DynamicServiceInfoResponseModel>() {
        public DynamicServiceInfoResponseModel createFromParcel(Parcel source) {
            return new DynamicServiceInfoResponseModel(source);
        }

        public DynamicServiceInfoResponseModel[] newArray(int size) {
            return new DynamicServiceInfoResponseModel[size];
        }
    };

}
