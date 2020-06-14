package com.example.dicodingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.dicodingapp.adapter.FavoriteWithoutCPAdapter;
import com.example.dicodingapp.db.FavoriteDatabase;
import com.example.dicodingapp.model.Favorite;

import java.util.ArrayList;
import java.util.List;

public class FavoriteWithoutCPActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavoriteDatabase db;
    private FavoriteWithoutCPAdapter favoriteWithoutCPAdapter;
    private List<Favorite> listFavorite = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_without_cp);

        recyclerView = findViewById(R.id.recyclerViewFavoriteWithoutCP);
        db = new FavoriteDatabase(this);

        listFavorite.addAll(db.allFavorites());

        favoriteWithoutCPAdapter = new FavoriteWithoutCPAdapter(this, listFavorite);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(favoriteWithoutCPAdapter);
    }
}
