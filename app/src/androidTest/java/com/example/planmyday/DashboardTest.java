package com.example.planmyday;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DashboardTest {
    @Rule
    public IntentsTestRule<DashboardActivity> activityRule = new IntentsTestRule<>(DashboardActivity.class);

    @Test
    public void testSelectLATour() throws InterruptedException {
        onView(withId(R.id.LAButton)).perform(click());
        Thread.sleep(500);
        Intents.intended(hasComponent(LosAngelesActivity.class.getName()));
    }

    @Test
    public void testSelectUSCTour() throws InterruptedException {
        onView(withId(R.id.USCButton)).perform(click());
        Thread.sleep(500);
        Intents.intended(hasComponent(USCActivity.class.getName()));
    }

    @Test
    public void testLogOut() throws InterruptedException {
        onView(withId(R.id.logoutButton)).perform(click());
        Thread.sleep(500);
        Intents.intended(hasComponent(LoginActivity.class.getName()));
    }
}
