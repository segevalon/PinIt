package com.sa.pinit.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sa.pinit.R;
import com.sa.pinit.model.Model;
import com.sa.pinit.model.Post;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    private final String TAG = "RecyclerViewAdapter";

    private LayoutInflater mInflater;
    private Model model;
    private Post post;

    public RecyclerViewAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);

        model = Model.getInstance();
    }

    public void onPostListChange() {
        Log.d(TAG, "onPostListChange()");
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.custom_row_adapter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        post = model.getMyPostList().get(position);
        holder.title.setText(post.getTitle());
        holder.date.setText(post.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return model.getMyPostList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.remove_btn)
        ImageView removeBtn;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            removeBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == removeBtn.getId()) {
                model.removePost(getAdapterPosition());
            }
        }
    }


}
