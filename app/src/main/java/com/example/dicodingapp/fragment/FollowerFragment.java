package com.example.dicodingapp.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dicodingapp.EndPoints;
import com.example.dicodingapp.R;
import com.example.dicodingapp.RetrofitClient;
import com.example.dicodingapp.adapter.FollowerAdapter;
import com.example.dicodingapp.model.Follower;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowerFragment extends Fragment {

    private RecyclerView recyclerView;
    private FollowerAdapter followerAdapter;
    private String TAG = "Follower fragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_follower, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewFollower);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String value = "";
        if (getArguments() != null) {
            value = getArguments().getString("key");
        }
        try {
            EndPoints endPoints = RetrofitClient.getRetrofitInstance().create(EndPoints.class);
            Call<List<Follower>> listCall = endPoints.getFollowersWithPath(value);
            listCall.enqueue(new Callback<List<Follower>>() {
                @Override
                public void onResponse(Call<List<Follower>> call, Response<List<Follower>> response) {
                    if (response.isSuccessful()){
                        setAdapter(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<Follower>> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setAdapter(List<Follower> body) {
        followerAdapter = new FollowerAdapter(body);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(followerAdapter);
        followerAdapter.notifyDataSetChanged();
    }
}
