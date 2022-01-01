package com.laioffer.tinnews.network;

import com.laioffer.tinnews.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    //create a "GET" request to "API"
    @GET("top-headlines")
    Call<NewsResponse> getTopHeadlines(@Query("country") String country);

    //create a "GET" request to "API"
    @GET("everything")
    Call<NewsResponse> getEverything(
            @Query("q") String query, @Query("pageSize") int pageSize);

}
