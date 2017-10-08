package by.dimanolog.farfor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import by.dimanolog.farfor.fragments.OfferListFragment;
import by.dimanolog.farfor.model.Category;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by Dimanolog on 08.10.2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, packageName="by.dimanolog.farfor")
public class OfferListFragmentsTest {

    @Test
    public void testOffetListFragment() {
        Category category = new Category();
        category.setId(5L);
        OfferListFragment offerListFragment = OfferListFragment.newInstance(category);
        SupportFragmentTestUtil.startVisibleFragment(offerListFragment, MainActivity.class,R.id.fragment_container);

        assertNotNull(offerListFragment);
    }

}
