package com.sa.pinit.screens.activities.auth;

import com.sa.pinit.screens.base.BasePresenter;

public interface AuthInterface {


    interface View {
        void showLoginFragment();

        void showRegisterFragment();

        void showResetFragment();

        void showErrorEmailEmpty();

        void showErrorPasswordEmpty();

        void showErrorPasswordMinimumLength();

        void signInWithEmailAndPassword(String email, String password);

        void signUpWithEmailAndPassword(String email, String password);

        void resetPasswordByEmail(String email);

        void showProgressDialog(String message);

        void dismissProgressDialog();

    }

    interface Presenter extends BasePresenter<View> {
        void showLoginFragment();

        void showRegisterFragment();

        void showResetFragment();

        void showErrorEmailEmpty();

        void showErrorPasswordEmpty();

        void showErrorPasswordMinimumLength();

        void signInWithEmailAndPassword(String email, String password);

        void signUpWithEmailAndPassword(String email, String password);

        void resetPasswordByEmail(String email);

        void showProgressDialog(String message);

        void dismissProgressDialog();

    }
}