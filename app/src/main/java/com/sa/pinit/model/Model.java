package com.sa.pinit.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sa.pinit.screens.activities.auth.AuthPresenter;
import com.sa.pinit.screens.activities.main.MainPresenter;
import com.sa.pinit.screens.fragments.map.MapPresenter;
import com.sa.pinit.screens.fragments.postCreate.PostCreatePresenter;
import com.sa.pinit.screens.fragments.postDetails.PostDetailsPresenter;
import com.sa.pinit.screens.fragments.postList.PostListPresenter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

public class Model implements ModelInterface {

    private String TAG = "Model";
    private static Model model_instance = null;
    private static MainPresenter mainPresenter = null;
    private static AuthPresenter authPresenter = null;
    private static MapPresenter mapPresenter = null;
    private static PostDetailsPresenter postDetailsPresenter = null;
    private static PostCreatePresenter postCreatePresenter = null;
    private static PostListPresenter postListPresenter = null;

    private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String lower = upper.toLowerCase(Locale.ROOT);
    private static final String digits = "0123456789";
    private static final String alphanum = upper + lower + digits;

    private HashMap<String, Post> postHashMap = new HashMap<>();
    private ArrayList<Post> allPostList = new ArrayList<>();
    private ArrayList<Post> myPostList = new ArrayList<>();

    private static DatabaseReference myRef;
    private static FirebaseAuth auth;
    private static FirebaseDatabase database;
    private static StorageReference mStorageRef;
    private long lastPostAddedTimeStamp = 0;

    private boolean firstLoad = true;


    public static Model getInstance() {
        if (model_instance == null) {

            model_instance = new Model();
            mainPresenter = MainPresenter.getInstance();
            authPresenter = AuthPresenter.getInstance();
            mapPresenter = MapPresenter.getInstance();
            postDetailsPresenter = PostDetailsPresenter.getInstance();
            postCreatePresenter = PostCreatePresenter.getInstance();
            postListPresenter = PostListPresenter.getInstance();

            auth = FirebaseAuth.getInstance();
            mStorageRef = FirebaseStorage.getInstance().getReference();
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("posts");
        }
        return model_instance;
    }

    @Override
    public String generatePostId() {
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 40; i++) {
            sb.append(alphanum.charAt(r.nextInt(alphanum.length())));
        }
        return sb.toString();
    }


    @Override
    public void savePost(Post post) {
        Log.d(TAG, "savePost()");
        myRef.child(auth.getUid()).child(post.getId()).setValue(post).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    lastPostAddedTimeStamp = new Date().getTime();
                }
            }
        });
        mainPresenter.showMapFragment();
    }

    @Override
    public void getAllPosts() {
        Log.d(TAG, "getAllPosts()");

        if (firstLoad) {
            firstLoad = false;
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Post post;
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        for (DataSnapshot d1 : d.getChildren()) {
                            post = d1.getValue(Post.class);
                            if (post != null && post.getOwnerID().equals(getCurrentUserEmail())) {
                                boolean exist = false;
                                for (Post p : myPostList) {
                                    if (p.getId().equals(post.getId())) {
                                        exist = true;
                                    }
                                }
                                if (!exist) {
                                    myPostList.add(post);
                                }
                            }
                            boolean exist = false;
                            for (Post p : allPostList) {
                                if (p.getId().equals(post.getId())) {
                                    exist = true;
                                }
                            }
                            if (!exist) {
                                allPostList.add(post);
                            }
                            Log.e(TAG, "Id = " + post.getId());
                            Log.e(TAG, "Title = " + post.getTitle());
                            Log.e(TAG, "Content = " + post.getContent());
                            Log.e(TAG, "Latitude = " + post.getLatitude());
                            Log.e(TAG, "Longitude = " + post.getLongitude());
                            Log.e(TAG, "ImageUrl = " + post.getImageUrl());
                            Log.e(TAG, "OwnerID = " + post.getOwnerID());
                            Log.e(TAG, "Timestamp = " + post.getTimestamp());
                        }
                    }
                    mapPresenter.onPostListReady(allPostList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            mapPresenter.onPostListReady(allPostList);
        }
    }

    @Override
    public void addMarkerToHash(String markerId, Post p) {
        Log.d(TAG, "addMarkerToHash()");
        postHashMap.put(markerId, p);
    }

    @Override
    public Post getPostByMarkerId(String markerId) {
        Log.d(TAG, "getPostByMarkerId()");
        return postHashMap.get(markerId);
    }

    @Override
    public String getCurrentUserEmail() {
        Log.d(TAG, "getCurrentUserEmail()");
        return auth.getCurrentUser().getEmail();
    }

    @Override
    public void validateEmailPass(String email, String password, boolean isLogin, boolean isRegister) {
        Log.d(TAG, "validateEmailPass()");
        if (email == null || email.equals("")) {
            authPresenter.showErrorEmailEmpty();
        } else if (password == null || password.equals("")) {
            authPresenter.showErrorPasswordEmpty();
        } else if (password.length() < 6) {
            authPresenter.showErrorPasswordMinimumLength();
        } else {
            if (isLogin) {
                authPresenter.signInWithEmailAndPassword(email, password);
            } else if (isRegister) {
                authPresenter.signUpWithEmailAndPassword(email, password);
            }
        }
    }

    @Override
    public void resetPasswordByEmail(String email) {
        Log.d(TAG, "resetPasswordByEmail()");
        if (email == null || email.equals("")) {
            authPresenter.showErrorEmailEmpty();
        } else {
            authPresenter.resetPasswordByEmail(email);
        }
    }

    @Override
    public void uploadPostImage(String id, File file) {
        Log.d(TAG, "uploadPostImage()");
        if (file != null) {
            Uri uri = Uri.fromFile(file);
            StorageReference riversRef = mStorageRef.child("images/" + id + ".jpg");

            riversRef.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                        }
                    });
        }
    }

    @Override
    public void saveBitmapToFile(Bitmap bitmap) {
        Log.d(TAG, "saveBitmapToFile()");
        File dir = new File(Environment.getExternalStorageDirectory() + "/PinIt/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File imageFile = new File(Environment.getExternalStorageDirectory() + "/PinIt/temp.jpg");
        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }

    @Override
    public boolean checkIfUserCanPublishPostNow() {
        Log.d(TAG, "checkIfUserCanPublishPostNow()");
        if (lastPostAddedTimeStamp == 0) {
            return true;
        } else return (new Date().getTime() - lastPostAddedTimeStamp) > (1000 * 60);
    }

    @Override
    public ArrayList<Post> getMyPostList() {
        Log.d(TAG, "getMyPostList()");
        return myPostList;
    }

    @Override
    public void removePost(int postPosition) {
        Log.d(TAG, "removePost()");
        final Post post = myPostList.get(postPosition);
        myRef.child(auth.getUid()).child(post.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    myPostList.remove(post);
                    allPostList.remove(post);
                    postListPresenter.onPostListChange();
                }
            }
        });
    }
}
