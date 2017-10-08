package by.dimanolog.farfor;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest2 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest2() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.fragment_category_choose_recycler_view),
                        withParent(withId(R.id.fragment_container)),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(2, click()));

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.offer_list_item_weight_txt), withText("Вес : 250.00 гр"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.fragment_category_choose_recycler_view),
                        withParent(withId(R.id.fragment_container)),
                        isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction appCompatTextView2 = onView(
                withId(R.id.fragment_offer_param));
        appCompatTextView2.perform(scrollTo(), replaceText("Вес : 250.00 гр\nКаллорийность : 127.428\nБелки : 2.432\nЖиры : 5.55\nУглеводы : 16.609\n"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Открыть боковую панель"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Контакты"), isDisplayed()));
        appCompatCheckedTextView.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(R.id.fragment_contacts_address1), withText("ул. Озeрная, 49, Калининград, Калининградская обл., Россия, 236029")));
        appCompatTextView3.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatTextView4 = onView(
                allOf(withId(R.id.fragment_contacts_address3), withText("ул. Кульман 5б, Минск, Беларусь, 220013")));
        appCompatTextView4.perform(scrollTo(), click());

    }

}
