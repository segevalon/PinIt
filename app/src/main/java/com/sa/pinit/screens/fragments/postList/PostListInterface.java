package com.sa.pinit.screens.fragments.postList;

import com.sa.pinit.model.Post;
import com.sa.pinit.screens.base.BasePresenter;

import java.util.ArrayList;

public interface PostListInterface {


    interface View{
        void onPostListChange();
    }

    interface Presenter extends BasePresenter<View> {
        ArrayList<Post> getMyPostList();

        void onPostListChange();
    }

}