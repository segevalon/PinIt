package com.sa.pinit.screens.fragments.auth.login;

import com.sa.pinit.screens.base.BasePresenter;

public interface LoginInterface {

    interface View {

    }

    interface Presenter extends BasePresenter<LoginInterface.View> {

        void showRegisterFragment();

        void showResetFragment();

        void validateEmailPass(String email,String passwords);

        void showProgressDialog(String message);

    }

}