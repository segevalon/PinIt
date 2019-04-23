package com.sa.pinit.screens.fragments.postList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sa.pinit.R;
import com.sa.pinit.adapters.RecyclerViewAdapter;
import com.sa.pinit.model.Post;

public class PostListFragment extends Fragment implements PostListInterface.View {

    private final String TAG = "PostListFragment";
    private PostListPresenter presenter;
    RecyclerView recyclerView;
    private RecyclerViewAdapter adapter = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_list, container, false);

        presenter = PostListPresenter.getInstance();
        presenter.onAttach(this);

        recyclerView = view.findViewById(R.id.recycler_view);
        if (adapter == null) {
            adapter = new RecyclerViewAdapter(getContext());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        }

        return view;
    }

    @Override
    public void onPostListChange() {
        Log.d(TAG,"onPostListChange()");
        adapter.onPostListChange();
    }

}



