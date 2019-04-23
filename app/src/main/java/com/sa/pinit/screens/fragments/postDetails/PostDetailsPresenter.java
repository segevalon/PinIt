package com.sa.pinit.screens.fragments.postDetails;

import android.util.Log;

import com.sa.pinit.model.Model;
import com.sa.pinit.model.Post;

public class PostDetailsPresenter implements PostDetailsInterface.Presenter {

    private String TAG = "PostDetailsPresenter";
    private static PostDetailsPresenter presenter_instance = null;
    private PostDetailsInterface.View view;
    private Model model;

    public static PostDetailsPresenter getInstance() {
        if (presenter_instance == null) {
            presenter_instance = new PostDetailsPresenter();
        }
        return presenter_instance;
    }

    @Override
    public void onAttach(PostDetailsInterface.View view) {
        Log.d(TAG,"onAttach()");
        this.view = view;
        model = Model.getInstance();
    }

    @Override
    public void onDetach() {
        Log.d(TAG,"onDetach()");
        this.view = null;
        model = null;
    }

    @Override
    public Post getPostByMarkerId(String markerId) {
        return model.getPostByMarkerId(markerId);
    }
}
