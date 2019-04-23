package com.sa.pinit.screens.fragments.postList;

import android.util.Log;

import com.sa.pinit.model.Model;
import com.sa.pinit.model.Post;

import java.util.ArrayList;

public class PostListPresenter implements PostListInterface.Presenter {

    private String TAG = "PostListPresenter";
    private static PostListPresenter presenter_instance = null;
    private PostListInterface.View view;
    private Model model;

    public static PostListPresenter getInstance() {
        if (presenter_instance == null) {
            presenter_instance = new PostListPresenter();
        }
        return presenter_instance;
    }

    @Override
    public void onAttach(PostListInterface.View view) {
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
    public ArrayList<Post> getMyPostList() {
        Log.d(TAG,"getMyPostList()");
        return model.getMyPostList();
    }

    @Override
    public void onPostListChange() {
        Log.d(TAG,"onPostListChange()");
        view.onPostListChange();
    }
}
