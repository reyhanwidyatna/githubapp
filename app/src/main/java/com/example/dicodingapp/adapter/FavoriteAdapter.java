package com.example.dicodingapp.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dicodingapp.MainActivity;
import com.example.dicodingapp.R;
import com.example.dicodingapp.db.FavoriteDatabase;
import com.example.dicodingapp.model.Favorite;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private List<Favorite> favorites;
    private Context context;
    private FavoriteDatabase db;

    public class FavoriteViewHolder extends RecyclerView.ViewHolder{

        ImageView avatar;
        TextView title;
        Button favoriteButton;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.favoriteIV);
            title = itemView.findViewById(R.id.favoriteTV);
            favoriteButton = itemView.findViewById(R.id.favoriteButtonList);
        }
    }

    public FavoriteAdapter(Context context, List<Favorite> favoriteList) {
        this.context = context;
        this.favorites = favoriteList;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        final Favorite favorite = favorites.get(position);
        try {
            String image = favorite.getImage();
            holder.title.setText(favorite.getTitle());
            Picasso.get().load(image).into(holder.avatar);
            holder.favoriteButton.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
            holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Do you want to remove this from your favorite");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                                    db = new FavoriteDatabase(v.getContext());
                                    db.deleteData(favorite);
                                    v.getContext().startActivity(intent);
                                    Toast.makeText(context, "Remove item from favorite", Toast.LENGTH_SHORT).show();
                                    ((Activity)context).finish();
                                }
                            });

                    builder.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder.create();
                    alert11.show();
                }
            });

        } catch (Exception e){
            e.getMessage();
        }
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

}
