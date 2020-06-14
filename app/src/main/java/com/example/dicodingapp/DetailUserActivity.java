package com.example.dicodingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.dicodingapp.adapter.ViewPagerAdapter;
import com.example.dicodingapp.db.FavoriteDatabase;
import com.example.dicodingapp.model.Favorite;
import com.example.dicodingapp.model.Items;
import com.example.dicodingapp.model.UserDetail;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailUserActivity extends AppCompatActivity {

    private ImageView avatarDetail;
    private Button favoriteButton;
    private TextView nameDetail, companyDetail, repositoryDetail, locationDetail, usernameDetail;
    private ProgressDialog progressDialog;
    private Items items;
    private FavoriteDatabase db;

    private String query, username, avatar;
    private int id;

    String TAG = "Detail User Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        initialize();
        getSearchDetail();
        viewPagerFromSearch();
        favoriteRespond();
    }

    private void initialize() {
        progressDialog = new ProgressDialog(DetailUserActivity.this);
        avatarDetail = findViewById(R.id.avatarDetailIV);
        nameDetail = findViewById(R.id.nameDetailTV);
        companyDetail = findViewById(R.id.companyDetailTV);
        repositoryDetail = findViewById(R.id.repositoryDetailTV);
        locationDetail = findViewById(R.id.locationDetailTV);
        usernameDetail = findViewById(R.id.usernameDetailTV);
        favoriteButton = findViewById(R.id.favoriteBT);

        db = new FavoriteDatabase(this);

        items = getIntent().getParcelableExtra("searchdetail");
        if (items != null){
            query = items.getLogin();
            username = items.getLogin();
            avatar = items.getAvatar_url();
            id = items.getId();
        }
    }

    private void getSearchDetail() {
        progressDialog.dismiss();
        EndPoints endPoints = RetrofitClient.getRetrofitInstance().create(EndPoints.class);
        Call<UserDetail> call = endPoints.getUserWithParams(query);
        call.enqueue(new Callback<UserDetail>() {
            @Override
            public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                if (response.isSuccessful()){
                    try {
                        String imageUrl = response.body().getAvatar_url();
                        Picasso.get().load(imageUrl).into(avatarDetail);
                        nameDetail.setText(response.body().getName());
                        usernameDetail.setText(response.body().getLogin());
                        companyDetail.setText(response.body().getCompany());
                        locationDetail.setText(response.body().getLocation());
                        repositoryDetail.setText(response.body().getRepos_url());
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDetail> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void viewPagerFromSearch(){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this, items.getLogin());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(viewPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabLayout);
        tabs.setupWithViewPager(viewPager);
    }

    private void favoriteRespond() {
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(FavoriteDatabase.KEY_ID, id);
                values.put(FavoriteDatabase.ITEM_TITLE, username);
                values.put(FavoriteDatabase.ITEM_IMAGE, avatar);
                values.put(FavoriteDatabase.FAVORITE_STATUS, "1");

                getContentResolver().insert(FavoriteProvider.CONTENT_URI, values);

                Favorite favorite = new Favorite(id, username, avatar, "1");
                db.addFavorite(favorite);
                Toast.makeText(DetailUserActivity.this,"Added item to favorite", Toast.LENGTH_LONG).show();
            }
        });
    }
}
