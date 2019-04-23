package com.sa.pinit.screens.fragments.postDetails;

import com.sa.pinit.model.Post;
import com.sa.pinit.screens.base.BasePresenter;

public interface PostDetailsInterface {


    interface View{
        void setPostDetailsToFields(Post post);
    }

    interface Presenter extends BasePresenter<View> {
        Post getPostByMarkerId(String markerId);
    }

}