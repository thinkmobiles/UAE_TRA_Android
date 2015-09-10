package com.uae.tra_smart_services.activity;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.uae.tra_smart_services.R;

import junit.framework.TestResult;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;
/**
 * Created by and on 10.09.15.
 */
@RunWith(AndroidJUnit4.class)
public class AuthorizationActivityTest
        extends ActivityInstrumentationTestCase2<AuthorizationActivity> {

    private AuthorizationActivity mActivity;

    public AuthorizationActivityTest() {
        super(AuthorizationActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
    }

    @Test
    public void testHeaderImageVisible(){
        assertNotNull(mActivity);
        onView(withId(R.id.ivLogo_FSL)).check(matches(isDisplayed()));
    }

    @Override
    protected TestResult createResult() {
        return super.createResult();
    }
}