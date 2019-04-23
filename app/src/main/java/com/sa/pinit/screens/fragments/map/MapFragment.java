package com.sa.pinit.screens.fragments.map;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sa.pinit.R;
import com.sa.pinit.adapters.InfoWindowAdapter;
import com.sa.pinit.model.Post;
import com.sa.pinit.screens.activities.main.MainPresenter;

import java.util.ArrayList;

import im.delight.android.location.SimpleLocation;

public class MapFragment extends Fragment implements MapInterface.View, OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, View.OnClickListener {


    private String TAG = "MapFragment";
    private MapPresenter presenter;
    private MainPresenter mainPresenter;
    private GoogleMap googleMap;
    private Location location = null;
    InfoWindowAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        Button createPostBtn = view.findViewById(R.id.create_new_post_btn);
        createPostBtn.setOnClickListener(this);
        MapView mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        presenter = MapPresenter.getInstance();
        mainPresenter = MainPresenter.getInstance();
        presenter.onAttach(this);

        return view;
    }


    @Override
    public void onMapReady(GoogleMap map) {
        Log.d(TAG, "onMapReady()");
        googleMap = map;
        adapter = new InfoWindowAdapter(getContext());
        SimpleLocation location = mainPresenter.getCurrentLocation();
        googleMap.setInfoWindowAdapter(adapter);
        googleMap.setOnInfoWindowClickListener(this);

        if (location != null) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
        } else {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(32.080481, 34.780527), 12));
        }
        presenter.getAllPosts();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_new_post_btn:
                presenter.showPostCreateFragment();
                break;

        }
    }

    @Override
    public void onPostListReady(ArrayList<Post> postList) {
        Log.d(TAG, "onPostListReady()");
        for (Post p : postList) {
            if (p.getOwnerID().equals(presenter.getCurrentUserEmail())) {
                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .title(p.getTitle())
                        .snippet(p.getOwnerID())
                        .position(new LatLng(Double.valueOf(p.getLatitude()), Double.valueOf(p.getLongitude()))));
                presenter.addMarkerToHash(marker.getId(), p);

            } else {
                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .title(p.getTitle())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .snippet(p.getOwnerID())
                        .position(new LatLng(Double.valueOf(p.getLatitude()), Double.valueOf(p.getLongitude()))));
                presenter.addMarkerToHash(marker.getId(), p);
            }
        }
    }

    @Override
    public void btnZoomPlusClicked() {
        Log.d(TAG, "btnZoomPlusClicked()");
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(googleMap.getCameraPosition().zoom + 1));
    }

    @Override
    public void btnZoomMinusClicked() {
        Log.d(TAG, "btnZoomMinusClicked()");
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(googleMap.getCameraPosition().zoom - 1));
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.d(TAG, "onInfoWindowClick()");
        mainPresenter.showPostDetailsFragment(marker.getId());
    }
}
