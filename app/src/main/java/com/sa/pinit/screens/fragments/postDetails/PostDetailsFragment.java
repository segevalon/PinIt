package com.sa.pinit.screens.fragments.postDetails;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sa.pinit.R;
import com.sa.pinit.model.Post;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PostDetailsFragment extends Fragment implements PostDetailsInterface.View {

    private final String TAG = "PostDetailsFragment";
    private PostDetailsPresenter presenter;
    private Post post;
    private String markerId;
    TextView postTitleText, postContentText, postOwnerText, postDateText;
    ImageView imageFromCamera;
    ProgressBar progress;
    private static StorageReference mStorageRef;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            markerId = getArguments().getString("markerId");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_details, container, false);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        postTitleText = view.findViewById(R.id.post_title_text);
        postContentText = view.findViewById(R.id.post_content_text);
        postOwnerText = view.findViewById(R.id.post_owner);
        postDateText = view.findViewById(R.id.post_date);
        imageFromCamera = view.findViewById(R.id.image_from_camera);
        progress = view.findViewById(R.id.progress);

        presenter = PostDetailsPresenter.getInstance();
        presenter.onAttach(this);
        post = presenter.getPostByMarkerId(markerId);
        Log.e(TAG, post.getOwnerID());

        setPostDetailsToFields(post);

        return view;
    }

    @Override
    public void setPostDetailsToFields(Post post) {
        progress.setVisibility(View.VISIBLE);
        postTitleText.setText(post.getTitle());
        postContentText.setText(post.getContent());
        postOwnerText.setText(post.getOwnerID());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date d = new Date(Long.parseLong(post.getTimestamp()));
        String postDate = format.format(d);
        postDateText.setText(postDate);

        mStorageRef.child(post.getImageUrl()).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Picasso.get().load(task.getResult()).into(imageFromCamera);
                    progress.setVisibility(View.GONE);
                }
            }
        });
    }
}



