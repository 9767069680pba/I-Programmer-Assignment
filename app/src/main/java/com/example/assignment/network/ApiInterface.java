package com.example.assignment.network;

import com.example.assignment.model.ImageDatum;

import java.util.List;
import java.util.concurrent.Callable;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("photos")
    Call<List<ImageDatum>> getAllImages();

}
