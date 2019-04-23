package com.sa.pinit.screens.activities.main;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sa.pinit.R;
import com.sa.pinit.screens.fragments.map.MapFragment;
import com.sa.pinit.screens.fragments.postCreate.PostCreateFragment;
import com.sa.pinit.screens.fragments.postDetails.PostDetailsFragment;
import com.sa.pinit.screens.fragments.postList.PostListFragment;

import im.delight.android.location.SimpleLocation;

public class MainActivity extends AppCompatActivity implements MainInterface.View {

    private String TAG = "MainActivity";
    private MainInterface.Presenter presenter;
    private MapFragment mapFragment;
    private PostListFragment listFragment;
    private FragmentTransaction transaction;
    private SimpleLocation location;


    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth auth;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            checkPermissions(this);
        } else {
            FirebaseApp.initializeApp(this);
            auth = FirebaseAuth.getInstance();
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("posts");

            presenter = MainPresenter.getInstance();
            presenter.onAttach(this);

            mapFragment = new MapFragment();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, mapFragment);
            transaction.commit();
            location = new SimpleLocation(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.list:
                showPostListFragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (location != null) {
            location.beginUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (location != null) {
            location.endUpdates();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDetach();
        }
    }

    @Override
    public void showMapFragment() {
        Log.d(TAG, "showMapFragment()");
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, mapFragment);
        transaction.addToBackStack(null).commit();
    }

    @Override
    public void showPostCreateFragment() {
        Log.d(TAG, "showPostCreateFragment()");
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new PostCreateFragment());
        transaction.addToBackStack(null).commit();
    }

    @Override
    public void showPostListFragment() {
        Log.d(TAG, "showPostListFragment()");
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new PostListFragment());
        transaction.addToBackStack(null).commit();
    }

    @Override
    public void showPostDetailsFragment(String markerId) {
        Log.d(TAG, "showPostDetailsFragment()");

        Bundle bundle = new Bundle();
        bundle.putString("markerId", markerId);
        PostDetailsFragment postDetailsFragment = new PostDetailsFragment();
        postDetailsFragment.setArguments(bundle);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, postDetailsFragment);
        transaction.addToBackStack(null).commit();
    }

    @Override
    public SimpleLocation getCurrentLocation() {
        return location;
    }

    public void checkPermissions(Context context) {
        Log.d(TAG, "checkPermissions()");

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("לאפליקציה זו נדרשות הרשאות - אנא אשר את כולן");
        builder.setPositiveButton("המשך", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            public void onClick(DialogInterface dialog, int id) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("יציאה", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void takePictureFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            presenter.onTakePictureFromCameraResult(imageBitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    checkPermissions(this);
                } else {
                    startActivity(new Intent(this, MainActivity.class));
                }
                break;
            default:
                break;
        }
    }
}
