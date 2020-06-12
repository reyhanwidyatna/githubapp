package com.example.dicodingapp.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dicodingapp.EndPoints;
import com.example.dicodingapp.R;
import com.example.dicodingapp.RetrofitClient;
import com.example.dicodingapp.adapter.FollowingAdapter;
import com.example.dicodingapp.model.Following;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowingFragment extends Fragment {

    private RecyclerView recyclerView;

    public FollowingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_following, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewFollowing);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String value = getArguments().getString("keyone");
        try {
            EndPoints endPoints = RetrofitClient.getRetrofitInstance().create(EndPoints.class);
            Call<List<Following>> call = endPoints.getFollowingWithPath(value);
            call.enqueue(new Callback<List<Following>>() {
                @Override
                public void onResponse(Call<List<Following>> call, Response<List<Following>> response) {
                    if (response.isSuccessful()){
                        setAdapter(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<Following>> call, Throwable t) {

                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setAdapter(List<Following> body) {
        FollowingAdapter followingAdapter = new FollowingAdapter(body);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(followingAdapter);
        followingAdapter.notifyDataSetChanged();
    }
}
