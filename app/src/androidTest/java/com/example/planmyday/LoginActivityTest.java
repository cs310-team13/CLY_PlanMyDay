package com.example.planmyday;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {

    @Rule
    public IntentsTestRule<LoginActivity> activityRule = new IntentsTestRule<>(LoginActivity.class);

    @Test
    public void testLoginWithEmptyFields() {
        // Attempt to login without entering email or password
        onView(withId(R.id.loginButton)).perform(click());
    }

    @Test
    public void testLoginWithValidCredentials() throws InterruptedException {
        // Type valid email and password
        onView(withId(R.id.emailEditText)).perform(typeText("xluo0963@usc.edu"), closeSoftKeyboard());
        onView(withId(R.id.passwordEditText)).perform(typeText("123456"), closeSoftKeyboard());

        onView(withId(R.id.loginButton)).perform(click());
        // Wait for a few seconds to allow the asynchronous operation to complete
        Thread.sleep(500);  // Waits for 0.5 seconds for firebase to authenticate
        Intents.intended(hasComponent(DashboardActivity.class.getName()));
    }

    @Test
    public void testLoginWithMisMatchedCredentials() throws InterruptedException {
        // Type valid email and password
        onView(withId(R.id.emailEditText)).perform(typeText("xluo0963@usc.edu"), closeSoftKeyboard());
        onView(withId(R.id.passwordEditText)).perform(typeText("111111"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
    }

    @Test
    public void testNavigationToRegistration() {
        // Click on the 'Create Account' button
        onView(withId(R.id.createAccountButton)).perform(click());
        Intents.intended(hasComponent(RegistrationActivity.class.getName()));
    }

    @Test
    public void testForgotPasswordNavigation() {
        // Click on the 'Forgot Password' button
        onView(withId(R.id.forgotPasswordButton)).perform(click());
        Intents.intended(hasComponent(ForgotPasswordActivity.class.getName()));
    }
}
