package com.sa.pinit.screens.fragments.auth.resetPassword;

import com.sa.pinit.screens.base.BasePresenter;

public interface ResetInterface {

    interface View {



    }

    interface Presenter extends BasePresenter<ResetInterface.View> {

        void showLoginFragment();

        void resetPasswordByEmail(String email);

        void showProgressDialog(String message);

    }

}