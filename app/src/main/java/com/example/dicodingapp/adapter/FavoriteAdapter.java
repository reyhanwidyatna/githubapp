package com.example.dicodingapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dicodingapp.R;
import com.squareup.picasso.Picasso;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private Cursor cursor;
    private Context context;

    public class FavoriteViewHolder extends RecyclerView.ViewHolder{

        ImageView avatar;
        TextView title;
        CardView cardView;
        Button favoriteButton;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.favoriteIV);
            title = itemView.findViewById(R.id.favoriteTV);
            favoriteButton = itemView.findViewById(R.id.favoriteButtonList);
            cardView = itemView.findViewById(R.id.cardViewFavorite);
        }
    }

    public FavoriteAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, final int position) {
        if (cursor.moveToPosition(position)){
            try {
                holder.title.setText(cursor.getString(cursor.getColumnIndex("title")));
                String image = cursor.getString(cursor.getColumnIndex("image"));
                Picasso.get().load(image).into(holder.avatar);
                holder.favoriteButton.setVisibility(View.GONE);
            } catch (Exception e){
                e.getMessage();
            }
        }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

}
