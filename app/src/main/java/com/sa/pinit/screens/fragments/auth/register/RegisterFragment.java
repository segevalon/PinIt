package com.sa.pinit.screens.fragments.auth.register;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sa.pinit.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterFragment extends Fragment implements RegisterInterface.View, View.OnClickListener {

    private String TAG = "RegisterFragment";
    private RegisterPresenter presenter;


    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_signup)
    Button btnSignUp;
    @BindView(R.id.btn_reset_password)
    Button btnResetPassword;
    @BindView(R.id.email)
    EditText editTextEmail;
    @BindView(R.id.password)
    EditText editTextPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);

        presenter = RegisterPresenter.getInstance();
        presenter.onAttach(this);

        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        btnResetPassword.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signup:
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                presenter.showProgressDialog("register");
                presenter.validateEmailPass(email, password);
                break;
            case R.id.btn_login:
                presenter.showLoginFragment();
                break;
            case R.id.btn_reset_password:
                presenter.showResetFragment();
                break;
        }
    }
}

