package com.sa.pinit.screens.fragments.postCreate;

import android.graphics.Bitmap;
import android.util.Log;

import com.sa.pinit.model.Model;
import com.sa.pinit.model.Post;
import com.sa.pinit.screens.activities.main.MainPresenter;

import java.io.File;

import im.delight.android.location.SimpleLocation;

public class PostCreatePresenter implements PostCreateInterface.Presenter {

    private String TAG = "PostCreatePresenter";
    private static PostCreatePresenter presenter_instance = null;
    private PostCreateInterface.View view;
    private Model model;
    private MainPresenter mainPresenter = null;

    public static PostCreatePresenter getInstance() {
        if (presenter_instance == null) {
            presenter_instance = new PostCreatePresenter();

        }
        return presenter_instance;
    }

    @Override
    public void onAttach(PostCreateInterface.View view) {
        Log.d(TAG, "onAttach()");
        this.view = view;
        model = Model.getInstance();
        mainPresenter = MainPresenter.getInstance();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach()");
        view = null;
    }

    @Override
    public void savePost(Post post) {
        Log.d(TAG, "savePost()");
        model.savePost(post);
    }

    @Override
    public String generatePostId() {
        Log.d(TAG, "generatePostId()");
        return model.generatePostId();
    }

    @Override
    public SimpleLocation getCurrentLocation() {
        Log.d(TAG, "getCurrentLocation()");
        return mainPresenter.getCurrentLocation();
    }

    @Override
    public String getCurrentUserEmail() {
        Log.d(TAG, "getCurrentUserEmail()");
        return model.getCurrentUserEmail();
    }

    @Override
    public void takePictureFromCamera() {
        Log.d(TAG, "takePictureFromCamera()");
        mainPresenter.takePictureFromCamera();
    }

    @Override
    public void onTakePictureFromCameraResult(Bitmap bitmap) {
        Log.d(TAG, "onTakePictureFromCameraResult()");
        view.onTakePictureFromCameraResult(bitmap);
    }

    @Override
    public void saveBitmapToFile(Bitmap bitmap) {
        Log.d(TAG, "saveBitmapToFile()");
        model.saveBitmapToFile(bitmap);
    }

    @Override
    public void uploadPostImage(String id, File file) {
        Log.d(TAG, "uploadPostImage()");
        model.uploadPostImage(id, file);
    }

    @Override
    public boolean checkIfUserCanPublishPostNow() {
        return model.checkIfUserCanPublishPostNow();
    }
}
