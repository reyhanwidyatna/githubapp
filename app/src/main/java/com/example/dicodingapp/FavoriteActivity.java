package com.example.dicodingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.dicodingapp.adapter.FavoriteAdapter;
import com.example.dicodingapp.db.FavoriteDatabase;
import com.example.dicodingapp.model.Favorite;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavoriteDatabase db;
    private FavoriteAdapter favoriteAdapter;
    private List<Favorite> listFavorite = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        recyclerView = findViewById(R.id.recyclerViewFavorite);
        db = new FavoriteDatabase(this);

        listFavorite.addAll(db.allFavorites());

        favoriteAdapter = new FavoriteAdapter(this, listFavorite);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(favoriteAdapter);
    }



}
