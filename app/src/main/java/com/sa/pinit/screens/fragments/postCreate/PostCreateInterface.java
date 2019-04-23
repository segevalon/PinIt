package com.sa.pinit.screens.fragments.postCreate;

import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;

import com.sa.pinit.model.Post;
import com.sa.pinit.screens.base.BasePresenter;

import java.io.File;

import im.delight.android.location.SimpleLocation;

public interface PostCreateInterface {

    interface View {
        void onTakePictureFromCameraResult(Bitmap bitmap);
    }

    interface Presenter extends BasePresenter<View> {
        void savePost(Post post);

        String generatePostId();

        SimpleLocation getCurrentLocation();

        String getCurrentUserEmail();

        void takePictureFromCamera();

        void onTakePictureFromCameraResult(Bitmap bitmap);

        void saveBitmapToFile(Bitmap bitmap);

        void uploadPostImage(String id,File file);

        boolean checkIfUserCanPublishPostNow();

    }

}