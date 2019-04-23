package com.sa.pinit.screens.fragments.map;

import android.location.Location;

import com.google.android.gms.maps.model.Marker;
import com.sa.pinit.model.Post;
import com.sa.pinit.screens.base.BasePresenter;

import java.util.ArrayList;

public interface MapInterface {


    interface View {
        void onPostListReady(ArrayList<Post> postList);

        void btnZoomPlusClicked();

        void btnZoomMinusClicked();
    }


    interface Presenter extends BasePresenter<View> {
        void showPostCreateFragment();

        void getAllPosts();

        void onPostListReady(ArrayList<Post> postList);

        void addMarkerToHash(String markerId, Post p);

        void btnZoomPlusClicked();

        void btnZoomMinusClicked();

        String getCurrentUserEmail();
    }
}