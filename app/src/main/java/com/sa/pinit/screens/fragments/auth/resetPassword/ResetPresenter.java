package com.sa.pinit.screens.fragments.auth.resetPassword;

import android.util.Log;

import com.sa.pinit.model.Model;
import com.sa.pinit.screens.activities.auth.AuthPresenter;

public class ResetPresenter implements ResetInterface.Presenter {

    private String TAG = " ResetPresenter";
    private static ResetPresenter presenter_instance = null;
    private ResetInterface.View view;
    private Model model;
    private AuthPresenter authPresenter = null;

    public static ResetPresenter getInstance() {
        if (presenter_instance == null) {
            presenter_instance = new ResetPresenter();
        }
        return presenter_instance;
    }

    @Override
    public void onAttach(ResetInterface.View view) {
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
    public void resetPasswordByEmail(String email) {
        Log.d(TAG, "resetPasswordByEmail()");
        model.resetPasswordByEmail(email);
    }

    @Override
    public void showProgressDialog(String message) {
        Log.d(TAG, "showProgressDialog()");
        authPresenter.showProgressDialog(message);
    }
}