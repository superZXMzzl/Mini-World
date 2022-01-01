package com.laioffer.tinnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.laioffer.tinnews.model.NewsResponse;
import com.laioffer.tinnews.network.NewsApi;
import com.laioffer.tinnews.network.RetrofitClient;

public class MainActivity extends AppCompatActivity {
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assign bottom nav view from ID
        BottomNavigationView navView = findViewById(R.id.nav_view);


        //assign nav fragment from ID
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        //create nav controller
        navController = navHostFragment.getNavController();
        //use nav controller connect each view with each nav
        NavigationUI.setupWithNavController(navView, navController);
        //"this" --> action bar
        NavigationUI.setupActionBarWithNavController(this, navController);

        //create a instance of "NewsApi" to handle all HTTP requests
        NewsApi api = RetrofitClient.newInstance().create(NewsApi.class);
        //API --> getTopHeadLines
        api.getTopHeadlines("US")
                //".enqueue" to store response into
                .enqueue(new Callback<NewsResponse>() {
                    //successfully send to Server
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        //case 1 --> response Ok
                        if (response.isSuccessful()) {
                            Log.d("getTopHeadlines", response.body().toString());
                        } else {
                        //case 2 --> response not Ok
                            Log.d("getTopHeadlines", response.toString());
                        }
                    }

                    //fail to send to Server
                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        Log.d("getTopHeadlines", t.toString());
                    }
                }
        );

    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }
}