package com.sa.pinit.screens.fragments.auth.register;

import android.util.Log;

import com.sa.pinit.model.Model;
import com.sa.pinit.screens.activities.auth.AuthPresenter;

public class RegisterPresenter implements RegisterInterface.Presenter {

    private String TAG = "RegisterPresenter";
    private static RegisterPresenter presenter_instance = null;
    private RegisterInterface.View view;
    private Model model;
    private AuthPresenter authPresenter = null;

    public static RegisterPresenter getInstance() {
        if (presenter_instance == null) {
            presenter_instance = new RegisterPresenter();
        }
        return presenter_instance;
    }

    @Override
    public void onAttach(RegisterInterface.View view) {
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
    public void showLoginFragment() {
        Log.d(TAG, "showLoginFragment()");
        authPresenter.showLoginFragment();
    }

    @Override
    public void showResetFragment() {
        Log.d(TAG, "showResetFragment()");
        authPresenter.showResetFragment();
    }

    @Override
    public void validateEmailPass(String email, String passwords) {
        Log.d(TAG, "validateEmailPass()");
        model.validateEmailPass(email, passwords, false, true);
    }

    @Override
    public void showProgressDialog(String message) {
        Log.d(TAG, "showProgressDialog()");
        authPresenter.showProgressDialog(message);
    }

}