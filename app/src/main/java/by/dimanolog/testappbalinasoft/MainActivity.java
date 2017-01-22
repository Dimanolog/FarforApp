package by.dimanolog.testappbalinasoft;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import by.dimanolog.testappbalinasoft.beans.Category;
import by.dimanolog.testappbalinasoft.beans.Offer;
import by.dimanolog.testappbalinasoft.dialogs.ProgressDialogFragment;
import by.dimanolog.testappbalinasoft.fragments.CategoryChooserFragment;
import by.dimanolog.testappbalinasoft.fragments.ContactsFragment;
import by.dimanolog.testappbalinasoft.fragments.OfferListFragment;
import by.dimanolog.testappbalinasoft.services.UfaFarforDataProvider;

import static by.dimanolog.testappbalinasoft.services.UfaFarforDataProvider.getInstance;

public class MainActivity extends AppCompatActivity
        implements CategoryChooserFragment.CategoryChooserFragmentCallback,
        OfferListFragment.OfferListFragmentCallback {
    public static final String TAG=MainActivity.class.getSimpleName();
    public static final String PROGRESS_DIALOG="ProgressDialog";

    private Category mCurrentCategory;
    private UfaFarforDataProvider mUfaFarforDataProvider;
    private ProgressDialogFragment mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                return onItemSelected(item.getItemId());
            }
        });
        mProgressDialog = new ProgressDialogFragment();

        mUfaFarforDataProvider = getInstance(this);
        if (!mUfaFarforDataProvider.isReady()) {
                new UfaFarforDataUpdateTask().execute();
        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
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

    }


    private boolean onItemSelected(int menuId) {


        Fragment fragment = null;

        switch (menuId) {
            case R.id.nav_catalog:
                fragment = CategoryChooserFragment.newInstance(mCurrentCategory);
                break;
            case R.id.nav_contacts:
                fragment= ContactsFragment.getNewInstance();
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

    private class UfaFarforDataUpdateTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.show(getSupportFragmentManager(), PROGRESS_DIALOG );

        }

        @Override
        protected Void doInBackground(Void... params) {
            mUfaFarforDataProvider.update();

            return null;
        }

        @Override
        protected void onPostExecute(Void Void) {
            super.onPostExecute(Void);
            mProgressDialog.dismiss();
            onItemSelected(R.id.nav_catalog);

        }
    }
}
