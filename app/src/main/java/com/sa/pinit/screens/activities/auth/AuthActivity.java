package com.sa.pinit.screens.activities.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sa.pinit.R;
import com.sa.pinit.screens.activities.main.MainActivity;
import com.sa.pinit.screens.fragments.auth.login.LoginFragment;
import com.sa.pinit.screens.fragments.auth.register.RegisterFragment;
import com.sa.pinit.screens.fragments.auth.resetPassword.ResetFragment;

public class AuthActivity extends AppCompatActivity implements AuthInterface.View {

    private final String TAG = "AuthActivity";
    private FragmentTransaction transaction;
    private FirebaseAuth auth;
    private AuthInterface.Presenter presenter;
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;
    private ResetFragment resetFragment;

    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        dialog = new ProgressDialog(this);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        presenter = AuthPresenter.getInstance();
        presenter.onAttach(this);
        loginFragment = new LoginFragment();
        resetFragment = new ResetFragment();
        registerFragment = new RegisterFragment();

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, loginFragment);
        transaction.commit();
    }

    @Override
    public void showLoginFragment() {
        Log.d(TAG, "showLoginFragment()");
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, loginFragment);
        transaction.commit();
    }

    @Override
    public void showRegisterFragment() {
        Log.d(TAG, "showRegisterFragment()");
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, registerFragment);
        transaction.commit();
    }

    @Override
    public void showResetFragment() {
        Log.d(TAG, "showResetFragment()");
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, resetFragment);
        transaction.commit();
    }

    @Override
    public void showErrorEmailEmpty() {
        Log.d(TAG, "showErrorEmailEmpty()");
        Toast.makeText(AuthActivity.this, getResources().getString(R.string.email_is_empty), Toast.LENGTH_LONG).show();
        dismissProgressDialog();
    }

    @Override
    public void showErrorPasswordEmpty() {
        Log.d(TAG, "showErrorPasswordEmpty()");
        Toast.makeText(AuthActivity.this, getResources().getString(R.string.password_is_empty), Toast.LENGTH_LONG).show();
        dismissProgressDialog();
    }

    @Override
    public void showErrorPasswordMinimumLength() {
        Log.d(TAG, "showErrorPasswordMinimumLength()");
        Toast.makeText(AuthActivity.this, getResources().getString(R.string.password_at_least_6), Toast.LENGTH_LONG).show();
        dismissProgressDialog();
    }

    @Override
    public void signInWithEmailAndPassword(String email, String password) {
        Log.d(TAG, "signInWithEmailAndPassword()");
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(AuthActivity.this, getResources().getString(R.string.failed_to_login), Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                dismissProgressDialog();
            }
        });
    }

    @Override
    public void signUpWithEmailAndPassword(String email, String password) {
        Log.d(TAG, "signUpWithEmailAndPassword()");
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(AuthActivity.this, getResources().getString(R.string.failed_create_user), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AuthActivity.this, getResources().getString(R.string.user_created_successfully), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AuthActivity.this, MainActivity.class));
                    finish();
                }
                dismissProgressDialog();
            }
        });
    }

    @Override
    public void resetPasswordByEmail(String email) {
        Log.d(TAG, "resetPasswordByEmail()");
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AuthActivity.this, getResources().getString(R.string.instructions_reset_password), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AuthActivity.this, getResources().getString(R.string.failed_reset_email), Toast.LENGTH_SHORT).show();
                        }
                        dismissProgressDialog();
                    }
                });
    }

    @Override
    public void showProgressDialog(String message) {
        Log.d(TAG, "showProgressDialog()");
        dialog.setMessage(message);
        dialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        Log.d(TAG, "dismissProgressDialog()");
        dialog.dismiss();
    }
}
