package com.sa.pinit.screens.activities.main;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.firebase.database.DataSnapshot;
import com.sa.pinit.model.Post;
import com.sa.pinit.screens.base.BasePresenter;

import java.util.ArrayList;

import im.delight.android.location.SimpleLocation;

public interface MainInterface {


    interface View {
        void showMapFragment();

        void showPostCreateFragment();

        void showPostListFragment();

        void showPostDetailsFragment(String markerId);

        SimpleLocation getCurrentLocation();

        void checkPermissions(Context context);

        void takePictureFromCamera();

    }

    interface Presenter extends BasePresenter<View> {
        void showMapFragment();

        void showPostCreateFragment();

        void showPostListFragment();

        void showPostDetailsFragment(String markerId);

        SimpleLocation getCurrentLocation();

        void takePictureFromCamera();

        void onTakePictureFromCameraResult(Bitmap bitmap);
    }


}