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
import com.example.dicodingapp.model.Follower;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FollowerAdapter extends RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder>{

    private List<Follower> dataList;

    public FollowerAdapter(List<Follower> dataList){
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public FollowerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_follower, parent, false);
        return new FollowerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowerViewHolder holder, int position) {
        Follower follower = dataList.get(position);
        String imageUrl = follower.getAvatar_url();
        Picasso.get().load(imageUrl).into(holder.avatar);
        holder.username.setText(follower.getLogin());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class FollowerViewHolder extends RecyclerView.ViewHolder{
        private ImageView avatar;
        private TextView username;
        public FollowerViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.followerAvatarIV);
            username = itemView.findViewById(R.id.followerUsernameTV);
        }
    }
}
