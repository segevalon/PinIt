package com.sa.pinit.model;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.sa.pinit.R;
import com.sa.pinit.screens.fragments.map.MapPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZoomButtons extends RelativeLayout implements View.OnClickListener {

    LayoutInflater mInflater;
    MapPresenter mapPresenter;
    @BindView(R.id.plus_btn)
    Button plus;
    @BindView(R.id.minus_btn)
    Button minus;


    public ZoomButtons(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
        init();
    }

    public ZoomButtons(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mInflater = LayoutInflater.from(context);
        init();
    }

    public ZoomButtons(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        init();
    }

    public void init() {
        View v = mInflater.inflate(R.layout.zoom_buttons, this, true);
        ButterKnife.bind(this, v);

        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
        mapPresenter = MapPresenter.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.plus_btn:
                mapPresenter.btnZoomPlusClicked();
                break;
            case R.id.minus_btn:
                mapPresenter.btnZoomMinusClicked();
                break;
        }
    }
}
