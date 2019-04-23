package com.sa.pinit.screens.fragments.auth.register;

import com.sa.pinit.screens.base.BasePresenter;

public interface RegisterInterface {

    interface View {

    }

    interface Presenter extends BasePresenter<RegisterInterface.View> {

        void showLoginFragment();

        void showResetFragment();

        void validateEmailPass(String email, String passwords);

        void showProgressDialog(String message);

    }

}