package com.uae.tra_smart_services.rest.model.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ak-buffalo on 20.08.15.
 */
public class InfoHubTransActionsListItemModel extends InfoHubListItemModel<InfoHubTransActionsListItemModel> implements Parcelable {

    public InfoHubTransActionsListItemModel() {
    }

    public InfoHubTransActionsListItemModel(Parcel in) {
    }

    public static final Parcelable.Creator<InfoHubTransActionsListItemModel> CREATOR
            = new Parcelable.Creator<InfoHubTransActionsListItemModel>() {
        public InfoHubTransActionsListItemModel createFromParcel(Parcel in) {
            return new InfoHubTransActionsListItemModel(in);
        }

        public InfoHubTransActionsListItemModel[] newArray(int size) {
            return new InfoHubTransActionsListItemModel[size];
        }
    };
}
