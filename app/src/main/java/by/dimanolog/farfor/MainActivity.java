package by.dimanolog.farfor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import by.dimanolog.farfor.dialogs.ProgressDialogFragment;
import by.dimanolog.farfor.fragments.CatalogFragment;
import by.dimanolog.farfor.fragments.ContactsFragment;
import by.dimanolog.farfor.fragments.OfferFragment;
import by.dimanolog.farfor.fragments.OfferListFragment;
import by.dimanolog.farfor.model.Category;
import by.dimanolog.farfor.model.Offer;
import by.dimanolog.farfor.services.FarforDataProvider;

import static by.dimanolog.farfor.services.FarforDataProvider.getInstance;

public class MainActivity extends AppCompatActivity
        implements CatalogFragment.CategoryChooserFragmentCallback,
        OfferListFragment.OfferListFragmentCallback,
        FarforDataProvider.FarforDataProviderCallback {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String ARG_CURRENT_CATEGORY = "current_category";

    private Category mCurrentCategory;
    private FarforDataProvider mUfaFarforDataProvider;
    private ProgressDialogFragment mProgressDialog;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {

            mCurrentCategory = (Category) savedInstanceState.getSerializable(ARG_CURRENT_CATEGORY);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return onItemSelected(item.getItemId());
            }
        });

        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                        if (fragment instanceof ContactsFragment) {
                            mNavigationView.setCheckedItem(R.id.nav_contacts);
                        } else {
                            mNavigationView.setCheckedItem(R.id.nav_catalog);
                        }
                    }
                });

        mProgressDialog = new ProgressDialogFragment();

        mUfaFarforDataProvider = getInstance(this);
        mUfaFarforDataProvider.setCallback(this);
        if (!mUfaFarforDataProvider.isReady()) {
            if (mUfaFarforDataProvider.isLoading()) {
                mProgressDialog = (ProgressDialogFragment) getSupportFragmentManager()
                        .findFragmentByTag(ProgressDialogFragment.FRAGMENT_TAG);
            }
            mUfaFarforDataProvider.update();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_CURRENT_CATEGORY, mCurrentCategory);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onCategoryItemClick(Category category) {
        mCurrentCategory = category;
        Fragment fragment = OfferListFragment.newInstance(mCurrentCategory);
        showFragment(fragment);
    }

    @Override
    public void onOfferItemClick(Offer offer) {
        Fragment fragment = OfferFragment.newInstance(offer);
        showFragment(fragment);
    }


    private boolean onItemSelected(int menuId) {
        Fragment fragment = null;
        switch (menuId) {
            case R.id.nav_catalog:
                fragment = CatalogFragment.newInstance(mCurrentCategory);
                break;
            case R.id.nav_contacts:
                fragment = ContactsFragment.newInstance();
                break;

            default:
                return false;

        }
        if (fragment != null) {
            showFragment(fragment);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void firstFragmentLoad() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, CatalogFragment.newInstance(mCurrentCategory))
                .commit();
    }

    @Override
    public void onStartLoad() {
        mProgressDialog.show(getSupportFragmentManager(), ProgressDialogFragment.FRAGMENT_TAG);
    }

    @Override
    public void onFinishLoad() {
        mProgressDialog.dismiss();
        firstFragmentLoad();
    }
}
