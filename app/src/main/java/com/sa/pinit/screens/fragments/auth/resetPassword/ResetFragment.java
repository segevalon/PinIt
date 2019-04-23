package com.sa.pinit.screens.fragments.auth.resetPassword;

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

public class ResetFragment extends Fragment implements ResetInterface.View, View.OnClickListener {

    private String TAG = " ResetFragment";
    private ResetPresenter presenter;

    @BindView(R.id.email)
    EditText editTextEmail;
    @BindView(R.id.btn_reset_password)
    Button btnReset;
    @BindView(R.id.btn_back)
    Button btnBack;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        View view = inflater.inflate(R.layout.fragment_reset, container, false);
        ButterKnife.bind(this, view);

        presenter = ResetPresenter.getInstance();
        presenter.onAttach(this);

        btnReset.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_back:
                presenter.showLoginFragment();
                break;
            case R.id.btn_reset_password:
                String email = editTextEmail.getText().toString().trim();
                presenter.showProgressDialog("reset password");
                presenter.resetPasswordByEmail(email);
                break;
        }
    }
}

