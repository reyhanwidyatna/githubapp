package com.example.dicodingapp;

import com.example.dicodingapp.model.Follower;
import com.example.dicodingapp.model.Following;
import com.example.dicodingapp.model.Search;
import com.example.dicodingapp.model.UserDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EndPoints {
    @GET("search/users")
    Call<Search> getUserWithQuery(
            @Query("q") String query
    );

    @GET("users/{username}")
    Call<UserDetail> getUserWithParams(
            @Path("username") String username
    );

    @GET("users/{username}/followers")
    Call<List<Follower>> getFollowersWithPath(
            @Path("username") String path
    );

    @GET("users/{username}/following")
    Call<List<Following>> getFollowingWithPath(
            @Path("username") String path
    );
}
