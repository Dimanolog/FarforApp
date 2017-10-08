package by.dimanolog.farfor;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Test;

/**
 * Created by Dimanolog on 08.10.2017.
 */
public class MainActivityTest {
    private ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testRunActivity() {
        mainActivity.launchActivity(new Intent());
        /*onView(withText(R.string.progress_dialog_title))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));*/
    }
    @Test
    public void testContactsFragment(){
        mainActivity.launchActivity(new Intent());



    }

}