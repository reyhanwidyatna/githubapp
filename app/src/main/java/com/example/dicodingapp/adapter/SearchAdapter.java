package com.example.dicodingapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dicodingapp.DetailUserActivity;
import com.example.dicodingapp.R;
import com.example.dicodingapp.model.Items;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{

    private List<Items> dataList;

    public SearchAdapter(List<Items> dataList){
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_view, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        final Items items = dataList.get(position);
        String imageUrl = items.getAvatar_url();
        Picasso.get().load(imageUrl).into(holder.avatar);
        holder.username.setText(items.getLogin());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailUserActivity.class);
                intent.putExtra("searchdetail", items);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder{
        private ImageView avatar;
        private TextView username;

        SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.usernameTV);
            avatar = itemView.findViewById(R.id.avatarIV);
        }
    }
}
