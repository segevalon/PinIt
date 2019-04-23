package com.sa.pinit.screens.fragments.map;

import android.util.Log;

import com.sa.pinit.model.Model;
import com.sa.pinit.model.Post;
import com.sa.pinit.screens.activities.main.MainPresenter;

import java.util.ArrayList;

public class MapPresenter implements MapInterface.Presenter {

    private String TAG = "MapPresenter";
    private static MapPresenter presenter_instance = null;
    private MapInterface.View view;
    private Model model;
    private MainPresenter mainPresenter = null;

    public static MapPresenter getInstance() {
        if (presenter_instance == null) {
            presenter_instance = new MapPresenter();
        }
        return presenter_instance;
    }


    @Override
    public void onAttach(MapInterface.View view) {
        Log.d(TAG, "onAttach()");
        this.view = view;
        model = Model.getInstance();
        mainPresenter = MainPresenter.getInstance();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach()");
        view = null;
        model = null;
    }

    @Override
    public void showPostCreateFragment() {
        Log.d(TAG, "showPostCreateFragment()");
        mainPresenter.showPostCreateFragment();
    }

    @Override
    public void getAllPosts() {
        Log.d(TAG, "getAllPosts()");
        model.getAllPosts();
    }

    @Override
    public void onPostListReady(ArrayList<Post> postList) {
        Log.d(TAG, "onPostListReady()");
        view.onPostListReady(postList);
    }

    @Override
    public void addMarkerToHash(String markerId, Post p) {
        Log.d(TAG, "addMarkerToHash()");
        model.addMarkerToHash(markerId, p);
    }

    @Override
    public void btnZoomPlusClicked() {
        Log.d(TAG, "btnZoomPlusClicked()");
        view.btnZoomPlusClicked();
    }

    @Override
    public void btnZoomMinusClicked() {
        Log.d(TAG, "btnZoomMinusClicked()");
        view.btnZoomMinusClicked();
    }

    @Override
    public String getCurrentUserEmail() {
        Log.d(TAG, "getCurrentUserEmail()");
       return model.getCurrentUserEmail();
    }
}