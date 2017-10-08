package by.dimanolog.farfor;

import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;

import by.dimanolog.farfor.fragments.OfferListFragment;
import by.dimanolog.farfor.model.Category;

/**
 * Created by Dimanolog on 08.10.2017.
 */

public class MainActivityTest {

    private ActivityController<MainActivity> mMainActivityController;

    @Test
    public void testMainActivity() {
        Category category = new Category();
        category.setId(5L);
        mMainActivityController = Robolectric.buildActivity(MainActivity.class);
        MainActivity mainActivity = mMainActivityController
                .create()
                .start()
                .resume()
                .get();

        mainActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, OfferListFragment.newInstance(category))
                .commit();

    }
}