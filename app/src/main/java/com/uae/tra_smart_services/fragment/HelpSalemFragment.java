package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.fragment.base.BaseFragment;

/**
 * Created by ak-buffalo on 11.08.15.
 */
public class HelpSalemFragment extends BaseFragment {
    public static HelpSalemFragment newInstance() {
        return new HelpSalemFragment();
    }
    @Override
    protected int getLayoutResource() {return R.layout.fragment_help_salem;}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    private EditText eturl, etDescription;
    @Override
    protected void initViews() {
        super.initViews();
        eturl = findView(R.id.eturl_FHS);
        etDescription = findView(R.id.etDescription_FHS);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_send, menu);
        toolbarTitleManager.setTitle(R.string.str_report_url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.menu.menu_send:
                // TODO implement sending logic
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}