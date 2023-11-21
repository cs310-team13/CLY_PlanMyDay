
package com.example.planmyday;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegistrationActivityTest {

    public static String generateUniqueEmail() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder email = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 10; i++) {
            email.append(characters.charAt(rnd.nextInt(characters.length())));
        }
        email.append(System.currentTimeMillis()).append("@test.com");
        return email.toString();
    }

    @Rule
    public IntentsTestRule<RegistrationActivity> activityRule = new IntentsTestRule<>(RegistrationActivity.class);

    @Test
    public void testCancelButton() {
        onView(withId(R.id.cancelRegistrationButton)).perform(click());
        Intents.intended(hasComponent(LoginActivity.class.getName()));
    }



    @Test
    public void testEmptyField() {
        onView(withId(R.id.nicknameEditText)).perform(typeText("nickname"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.emailEditText)).perform(typeText("test@example.com"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.passwordEditText)).perform(typeText("password"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.confirmPasswordEditText)).perform(typeText("password"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.dateOfBirthEditText)).perform((typeText("01012023")), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.registerButton)).perform(click());
        // stays on the current page, click on register again
        onView(withId(R.id.registerButton)).perform(click());
    }

    @Test
    public void testInvalidEmail() {
        onView(withId(R.id.nicknameEditText)).perform(typeText("nickname"), ViewActions.closeSoftKeyboard());
        // does not end in @xxx.xx format
        onView(withId(R.id.emailEditText)).perform(typeText("invalid"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.passwordEditText)).perform(typeText("password"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.confirmPasswordEditText)).perform(typeText("password"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.dateOfBirthEditText)).perform((typeText("01012023")), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.registerButton)).perform(click());
        // stays on the current page, click on register again
        onView(withId(R.id.registerButton)).perform(click());
    }

    @Test
    public void testMatchedPassword() throws InterruptedException {
        onView(withId(R.id.nicknameEditText)).perform(typeText("nickname"), ViewActions.closeSoftKeyboard());
        // need to make sure this email is deleted in firebase before running this test again
        onView(withId(R.id.emailEditText)).perform(typeText(generateUniqueEmail()), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.passwordEditText)).perform(typeText("password"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.confirmPasswordEditText)).perform(typeText("password"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.dateOfBirthEditText)).perform((typeText("01012023")), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.registerButton)).perform(click());

        Thread.sleep(500);  // Waits for 0.5 seconds for firebase to authenticate
        Intents.intended(hasComponent(VerificationActivity.class.getName()));
    }

    @Test
    public void testMisMatchedCPassword() throws InterruptedException {
        onView(withId(R.id.nicknameEditText)).perform(typeText("nickname"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.emailEditText)).perform(typeText(generateUniqueEmail()), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.passwordEditText)).perform(typeText("password"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.confirmPasswordEditText)).perform(typeText("different"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.dateOfBirthEditText)).perform((typeText("01012023")), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.registerButton)).perform(click());

        Thread.sleep(500);  // Waits for 0.5 seconds for firebase to authenticate
        onView(withId(R.id.registerButton)).perform(click()); // stay on the current page
    }
}

