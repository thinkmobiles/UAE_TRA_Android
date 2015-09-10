package com.uae.tra_smart_services.activity;

import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestResult;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Andrey Korneychuk on 10.09.15.
 */
public class HomeActivityTest
        extends ActivityInstrumentationTestCase2<HomeActivity> {

    private HomeActivity mActivity;

    public HomeActivityTest() {
        super(HomeActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
    }

    @Test
    public void testHexagonHeader(){
        assertNotNull(mActivity);
    }

    @Override
    protected TestResult createResult() {
        return super.createResult();
    }
}