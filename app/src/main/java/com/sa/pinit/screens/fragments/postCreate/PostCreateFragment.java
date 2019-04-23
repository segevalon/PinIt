package com.sa.pinit.screens.fragments.postCreate;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sa.pinit.R;
import com.sa.pinit.model.Post;

import java.io.File;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import im.delight.android.location.SimpleLocation;


public class PostCreateFragment extends Fragment implements PostCreateInterface.View, View.OnClickListener {

    private String TAG = "PostCreateFragment";
    private View view;
    private PostCreatePresenter presenter;
    @BindView(R.id.create_post_btn)
    Button createPostBtn;
    @BindView(R.id.post_title_edit)
    EditText postTitleEdit;
    @BindView(R.id.post_content_edit)
    EditText postContentEdit;
    @BindView(R.id.image_from_camera)
    ImageView imageFromCamera;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_post_create, container, false);
        ButterKnife.bind(this, view);

        createPostBtn.setOnClickListener(this);
        imageFromCamera.setOnClickListener(this);
        presenter = PostCreatePresenter.getInstance();
        presenter.onAttach(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_post_btn:
                if (presenter.checkIfUserCanPublishPostNow()) {

                    SimpleLocation l = presenter.getCurrentLocation();
                    String id = presenter.generatePostId();
                    String title = postTitleEdit.getText().toString().trim();
                    String content = postContentEdit.getText().toString().trim();
                    String lat = String.valueOf(l.getLatitude());
                    String lng = String.valueOf(l.getLongitude());

                    presenter.uploadPostImage(id, new File(Environment.getExternalStorageDirectory() + "/PinIt/temp.jpg"));

                    Log.e(TAG, "id = " + id + "\n" + "title = " + title + "\n" + "content = " + content + "\n" + "lat = " + lat + "\n" + "lng = " + lng);

                    presenter.savePost(new Post(id, title, content, "images/" + id + ".jpg", lat, lng, presenter.getCurrentUserEmail(), String.valueOf(new Date().getTime())));
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.publish_limit), Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.image_from_camera:
                presenter.takePictureFromCamera();
                break;
        }
    }

    @Override
    public void onTakePictureFromCameraResult(Bitmap bitmap) {
        Log.d(TAG, "onTakePictureFromCameraResult()");
        imageFromCamera.setImageBitmap(bitmap);
        presenter.saveBitmapToFile(bitmap);
    }
}
