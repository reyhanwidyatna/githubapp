package com.example.dicodingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dicodingapp.R;
import com.example.dicodingapp.model.Following;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>{

    private List<Following> dataList;

    public FollowingAdapter(List<Following> dataList){
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public FollowingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_follower, parent, false);
        return new FollowingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowingViewHolder holder, int position) {
        Following following = dataList.get(position);
        String imageUrl = following.getAvatar_url();
        Picasso.get().load(imageUrl).into(holder.avatar);
        holder.username.setText(following.getLogin());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class FollowingViewHolder extends RecyclerView.ViewHolder{
        private ImageView avatar;
        private TextView username;
        public FollowingViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.followerAvatarIV);
            username = itemView.findViewById(R.id.followerUsernameTV);
        }
    }
}
