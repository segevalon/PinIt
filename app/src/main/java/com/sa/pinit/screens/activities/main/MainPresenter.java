package com.sa.pinit.screens.activities.main;

import android.graphics.Bitmap;
import android.util.Log;

import com.sa.pinit.model.Model;
import com.sa.pinit.screens.fragments.map.MapPresenter;
import com.sa.pinit.screens.fragments.postCreate.PostCreatePresenter;
import com.sa.pinit.screens.fragments.postDetails.PostDetailsPresenter;

import im.delight.android.location.SimpleLocation;

public class MainPresenter implements MainInterface.Presenter {

    private String TAG = "MainPresenter";
    private static MainPresenter presenter_instance = null;
    private static MapPresenter mapPresenter = null;
    private static PostCreatePresenter postCreatePresenter = null;
    private static PostDetailsPresenter postDetailsPresenter = null;
    private MainInterface.View view;
    private Model model;

    public static MainPresenter getInstance() {
        if (presenter_instance == null) {
            presenter_instance = new MainPresenter();
        }
        return presenter_instance;
    }

    @Override
    public void onAttach(MainInterface.View view) {
        Log.d(TAG, "onAttach()");
        this.view = view;
        model = Model.getInstance();
        mapPresenter = MapPresenter.getInstance();
        postCreatePresenter = PostCreatePresenter.getInstance();
        postDetailsPresenter = PostDetailsPresenter.getInstance();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach()");
        view = null;
        model = null;
    }

    @Override
    public void showMapFragment() {
        Log.d(TAG, "showMapFragment()");
        view.showMapFragment();
    }

    @Override
    public void showPostCreateFragment() {
        Log.d(TAG, "showPostCreateFragment()");
        view.showPostCreateFragment();
    }

    @Override
    public void showPostListFragment() {
        Log.d(TAG, "showPostListFragment()");
        view.showPostListFragment();
    }

    @Override
    public void showPostDetailsFragment(String markerId) {
        Log.d(TAG, "showPostDetailsFragment()");
        view.showPostDetailsFragment(markerId);
    }

    @Override
    public SimpleLocation getCurrentLocation() {
        Log.d(TAG, "getCurrentLocation()");
        return view.getCurrentLocation();
    }

    @Override
    public void takePictureFromCamera() {
        Log.d(TAG, "takePictureFromCamera()");
        view.takePictureFromCamera();
    }

    @Override
    public void onTakePictureFromCameraResult(Bitmap bitmap) {
        Log.d(TAG, "onTakePictureFromCameraResult()");
        postCreatePresenter.onTakePictureFromCameraResult(bitmap);
    }
}
