package com.example.dicodingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dicodingapp.adapter.SearchAdapter;
import com.example.dicodingapp.model.Items;
import com.example.dicodingapp.model.Search;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private ProgressDialog progressDialog;
    private EditText searchUser;
    private Button searchButton;
    public static String CHANNEL_ID = "channelNotification";
    public static CharSequence CHANNEL_NAME = "dicodingChannel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        searchData();
        createNotificationChannel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
            finish();
        }
        if (item.getItemId() == R.id.action_favorite){
            Intent fIntent = new Intent(this, FavoriteActivity.class);
            startActivity(fIntent);
        }

        if (item.getItemId() == R.id.action_reminder){
            Intent rIntent = new Intent(this, ReminderActivity.class);
            startActivity(rIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initialize() {
        recyclerView = findViewById(R.id.recyclerView);
        searchUser = findViewById(R.id.searchET);
        searchButton = findViewById(R.id.searchUserBT);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.dismiss();
    }

    private void searchData() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Loading....");
                progressDialog.show();
                EndPoints endPoints = RetrofitClient.getRetrofitInstance().create(EndPoints.class);
                Call<Search> searchCall = endPoints.getUserWithQuery(searchUser.getText().toString());
                searchCall.enqueue(new Callback<Search>() {
                    @Override
                    public void onResponse(Call<Search> call, Response<Search> response) {
                        if (response.isSuccessful()){
                            progressDialog.dismiss();
                            setSearchAdapter(response.body().getItems());
                            searchUser.setText("");
                        } else {
                            Toast.makeText(MainActivity.this, "Data " + searchUser.getText().toString() + " not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Search> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void setSearchAdapter(List<Items> items) {
        searchAdapter = new SearchAdapter(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_NAME.toString());

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
