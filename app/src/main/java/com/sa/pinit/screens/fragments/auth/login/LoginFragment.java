package com.sa.pinit.screens.fragments.auth.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sa.pinit.R;


public class LoginFragment extends Fragment implements LoginInterface.View, View.OnClickListener {

    private final String TAG = "LoginFragment";
    private LoginPresenter presenter;
    private EditText editTextEmail, editTextPassword;
//    @BindView(R.id.btn_signup)
//    Button btnSignUp;
//    @BindView(R.id.btn_login)
//    Button btnLogin;
//    @BindView(R.id.btn_reset_password)
//    Button btnReset;
//    @BindView(R.id.email)
//    EditText editTextEmail;
//    @BindView(R.id.password)
//    EditText editTextPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        //ButterKnife.bind(view);

        presenter = LoginPresenter.getInstance();
        presenter.onAttach(this);

        editTextEmail = view.findViewById(R.id.email);
        editTextPassword = view.findViewById(R.id.password);
        Button btnSignUp = view.findViewById(R.id.btn_signup);
        btnSignUp.setOnClickListener(this);
        Button btnLogin = view.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        Button btnReset = view.findViewById(R.id.btn_reset_password);
        btnReset.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signup:
                presenter.showRegisterFragment();
                break;
            case R.id.btn_login:
                String email = editTextEmail.getText().toString();
                final String password = editTextPassword.getText().toString();
                presenter.showProgressDialog("login");
                presenter.validateEmailPass(email, password);
                break;
            case R.id.btn_reset_password:
                presenter.showResetFragment();
                break;
        }
    }
}

