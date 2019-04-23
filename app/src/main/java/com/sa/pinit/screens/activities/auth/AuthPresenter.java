package com.sa.pinit.screens.activities.auth;

import android.util.Log;

import com.sa.pinit.model.Model;
import com.sa.pinit.screens.fragments.auth.login.LoginPresenter;
import com.sa.pinit.screens.fragments.auth.register.RegisterPresenter;
import com.sa.pinit.screens.fragments.auth.resetPassword.ResetPresenter;

public class AuthPresenter implements AuthInterface.Presenter {

    private String TAG = "AuthPresenter";
    private static AuthPresenter presenter_instance = null;
    private static ResetPresenter resetPresenter = null;
    private static LoginPresenter loginPresenter = null;
    private static RegisterPresenter registerPresenter = null;
    private AuthInterface.View view;
    private Model model;

    public static AuthPresenter getInstance() {
        if (presenter_instance == null) {
            presenter_instance = new AuthPresenter();
        }
        return presenter_instance;
    }

    @Override
    public void onAttach(AuthInterface.View view) {
        Log.d(TAG, "onAttach()");
        this.view = view;
        model = Model.getInstance();
        resetPresenter = ResetPresenter.getInstance();
        loginPresenter = LoginPresenter.getInstance();
        registerPresenter = RegisterPresenter.getInstance();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach()");
        view = null;
        model = null;
    }

    @Override
    public void showLoginFragment() {
        Log.d(TAG, "showLoginFragment()");
        view.showLoginFragment();
    }

    @Override
    public void showRegisterFragment() {
        Log.d(TAG, "showRegisterFragment()");
        view.showRegisterFragment();
    }

    @Override
    public void showResetFragment() {
        Log.d(TAG, "showResetFragment()");
        view.showResetFragment();
    }

    @Override
    public void showErrorEmailEmpty() {
        Log.d(TAG, "showErrorEmailEmpty()");
        view.showErrorEmailEmpty();
    }

    @Override
    public void showErrorPasswordEmpty() {
        Log.d(TAG, "showErrorPasswordEmpty()");
        view.showErrorPasswordEmpty();
    }

    @Override
    public void showErrorPasswordMinimumLength() {
        Log.d(TAG, "showErrorPasswordMinimumLength()");
        view.showErrorPasswordMinimumLength();
    }

    @Override
    public void signInWithEmailAndPassword(String email, String password) {
        Log.d(TAG, "signInWithEmailAndPassword()");
        view.signInWithEmailAndPassword(email, password);
    }

    @Override
    public void signUpWithEmailAndPassword(String email, String password) {
        Log.d(TAG, "signUpWithEmailAndPassword()");
        view.signUpWithEmailAndPassword(email, password);
    }

    @Override
    public void resetPasswordByEmail(String email) {
        Log.d(TAG, "resetPasswordByEmail()");
        view.resetPasswordByEmail(email);
    }

    @Override
    public void showProgressDialog(String message) {
        Log.d(TAG, "showProgressDialog()");
        view.showProgressDialog(message);
    }

    @Override
    public void dismissProgressDialog() {
        Log.d(TAG, "dismissProgressDialog()");
        view.dismissProgressDialog();
    }
}
