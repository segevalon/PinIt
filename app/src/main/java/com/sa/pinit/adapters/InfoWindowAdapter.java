package com.sa.pinit.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sa.pinit.R;
import com.sa.pinit.model.Model;
import com.sa.pinit.model.Post;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final String TAG = "InfoWindowAdapter";
    private final View mWindow;
    private Model model;
    private static StorageReference mStorageRef;
    Post post;

    public InfoWindowAdapter(Context context) {
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
        model = Model.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

    }

    private void rendowWindowText(Marker marker, final View view) {

        Log.d(TAG, "rendowWindowText()");
        post = model.getPostByMarkerId(marker.getId());
        if (post != null) {

            String postImageUrl = post.getImageUrl();
            String postOwnerId = post.getOwnerID();
            String postTitle = post.getTitle();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            Date d = new Date(Long.parseLong(post.getTimestamp()));
            String postDate = format.format(d);

            TextView titleTextView = view.findViewById(R.id.title);
            TextView ownerIdTextView = view.findViewById(R.id.owner_id);
            TextView date = view.findViewById(R.id.date);

            if (!postTitle.equals("")) {
                titleTextView.setText(postTitle);
            } else {
                titleTextView.setText("");
            }
            if (!postOwnerId.equals("")) {
                ownerIdTextView.setText(postOwnerId);
            } else {
                ownerIdTextView.setText("");
            }
            if (postDate != null) {
                date.setText(postDate);
            } else {
                date.setText("");
            }
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        Log.d(TAG, "getInfoWindow()");
        rendowWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        Log.d(TAG, "getInfoContents()");
        rendowWindowText(marker, mWindow);
        return mWindow;
    }
}