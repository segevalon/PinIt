package com.sa.pinit.model;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.DataSnapshot;

import java.io.File;
import java.util.ArrayList;

public interface ModelInterface {


    String generatePostId();

    void savePost(Post post);

    void getAllPosts();

    void addMarkerToHash(String markerId, Post p);

    Post getPostByMarkerId(String markerId);

    String getCurrentUserEmail();

    void validateEmailPass(String email, String passwords, boolean isLogin, boolean isRegister);

    void resetPasswordByEmail(String email);

    void saveBitmapToFile(Bitmap bitmap);

    void uploadPostImage(String id,File file);

    boolean checkIfUserCanPublishPostNow();

    ArrayList<Post> getMyPostList();

    void removePost(int postPosition);

}
