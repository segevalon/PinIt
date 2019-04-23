package com.sa.pinit.screens.fragments.auth.login;

import android.util.Log;

import com.sa.pinit.model.Model;
import com.sa.pinit.screens.activities.auth.AuthPresenter;

public class LoginPresenter implements LoginInterface.Presenter {

    private String TAG = "LoginPresenter";
    private static LoginPresenter presenter_instance = null;
    private LoginInterface.View view;
    private Model model;
    private AuthPresenter authPresenter = null;

    public static LoginPresenter getInstance() {
        if (presenter_instance == null) {
            presenter_instance = new LoginPresenter();
        }
        return presenter_instance;
    }

    @Override
    public void onAttach(LoginInterface.View view) {
        Log.d(TAG, "onAttach()");
        this.view = view;
        model = Model.getInstance();
        authPresenter = AuthPresenter.getInstance();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach()");
        this.view = null;
        model = null;
    }

    @Override
    public void showRegisterFragment() {
        Log.d(TAG, "showRegisterFragment()");
        authPresenter.showRegisterFragment();
    }

    @Override
    public void showResetFragment() {
        Log.d(TAG, "showResetFragment()");
        authPresenter.showResetFragment();
    }

    @Override
    public void validateEmailPass(String email, String passwords) {
        Log.d(TAG, "validateEmailPass()");
        model.validateEmailPass(email, passwords, true, false);
    }

    @Override
    public void showProgressDialog(String message) {
        Log.d(TAG, "showProgressDialog()");
        authPresenter.showProgressDialog(message);
    }


}