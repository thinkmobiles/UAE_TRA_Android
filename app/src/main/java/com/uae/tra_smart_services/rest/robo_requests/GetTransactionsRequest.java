package com.uae.tra_smart_services.rest.robo_requests;

import com.uae.tra_smart_services.rest.TRAServicesAPI;
import com.uae.tra_smart_services.rest.model.response.GetTransactionResponseModel;

/**
 * Created by Vitaliy on 05/10/2015.
 */
public class GetTransactionsRequest extends BaseRequest<GetTransactionResponseModel.List, TRAServicesAPI> {

    private int mPage, mCount, mAsc;

    public GetTransactionsRequest(final int _page, final int _count, final int _asc) {
        super(GetTransactionResponseModel.List.class, TRAServicesAPI.class);
        mPage = _page;
        mCount = _count;
//        mAsc = _asc;
    }

    @Override
    public GetTransactionResponseModel.List loadDataFromNetwork() throws Exception {
        return getService().getTransactions(mPage, mCount, mAsc);
    }
}
