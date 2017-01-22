package by.dimanolog.testappbalinasoft.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import by.dimanolog.testappbalinasoft.App;
import by.dimanolog.testappbalinasoft.R;


/**
 * Created by Dimanolog on 21.01.2017.
 */

public class ContactsFragment extends Fragment {
    private static final String TAG = ContactsFragment.class.getSimpleName();

    private MapView mGooglrMapView;
    private GoogleMap mGoogleMap;
    private GoogleApiClient mClient;
    private Location mCurrentLocation;

    public static ContactsFragment getNewInstance() {
        return new ContactsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        startCheckLocation();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .build();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        mGooglrMapView = (MapView) view.findViewById(R.id.fragment_contacts_map);
        mGooglrMapView.onCreate(savedInstanceState);

        if (mGooglrMapView != null) {
            mGooglrMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mGoogleMap = googleMap;

                }
            });

        }

        return view;
    }

    void startCheckLocation() {
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(5000);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "ACCESS_FINE_LOCATION permission error");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, App.PERMISSION_ACCESS_FINE_LOCATION);
            }
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "ACCESS_COARSE_LOCATION permission error");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, App.MY_PERMISSION_ACCESS_COARSE_LOCATION);
            }

            return;
        }
        LocationServices.FusedLocationApi
                .requestLocationUpdates(mClient, request, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        mCurrentLocation = location;
                        updateUI();
                    }
                });

    }

    private void updateUI() {

        if (mGoogleMap == null) {
            return;
        }

        LatLng myPoint = new LatLng(
                mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());


        MarkerOptions myMarker = new MarkerOptions()
                .position(myPoint)
                .title("я");


        mGoogleMap.clear();

        mGoogleMap.addMarker(myMarker);

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(myPoint)
                .build();

        int margin = getResources().getDimensionPixelSize(R.dimen.map_inset_margin);
        CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds, 10);
        mGoogleMap.animateCamera(update);
    }

    @Override
    public void onStart() {
        mGooglrMapView.onStart();
        super.onStart();
        mClient.connect();

    }

    @Override
    public void onResume() {
        mGooglrMapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mGooglrMapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGooglrMapView.onStop();
        mClient.disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGooglrMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mGooglrMapView.onLowMemory();
    }

}

