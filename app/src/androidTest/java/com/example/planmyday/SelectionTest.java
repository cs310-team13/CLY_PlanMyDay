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
public class SelectionTest {
    @Rule
    public IntentsTestRule<LosAngelesActivity> activityRule = new IntentsTestRule<>(LosAngelesActivity.class);

    @Test
    public void testInvalidSelection() throws InterruptedException {
        onView(withId(R.id.submitButton)).perform(click());
    }

    @Test
    public void testValidSelection() throws InterruptedException {
        onView(withId(R.id.checkbox_Crypto)).perform(click());
        onView(withId(R.id.checkbox_Disney)).perform(click());
        onView(withId(R.id.checkbox_Venice)).perform(click());
        onView(withId(R.id.submitButton)).perform(click());

        Thread.sleep(500);
        Intents.intended(hasComponent(DaySelectionActivity.class.getName()));
    }
}
