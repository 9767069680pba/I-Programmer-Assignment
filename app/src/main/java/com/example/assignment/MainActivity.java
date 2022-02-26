package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.assignment.adapter.ImageAdapter;
import com.example.assignment.adapter.TableAdapter;
import com.example.assignment.model.ImageDatum;
import com.example.assignment.network.ApiConfig;
import com.example.assignment.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerViewImage;
    RecyclerView recyclerViewTable;

    ImageAdapter imageAdapter;
    TableAdapter tableAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewImage = findViewById(R.id.rec_view_image);
        recyclerViewTable = findViewById(R.id.rec_view_comparison_table);

        setRecyclerAdapter();

        callApi();

        getMutableData();

    }

    private void getMutableData() {
        imageAdapter.getImageDatumMutableLiveData().observe(this, new Observer<ImageDatum>() {
            @Override
            public void onChanged(ImageDatum imageDatum) {
                tableAdapter.setLDataItem(imageDatum);
            }
        });

        imageAdapter.getImageId().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                tableAdapter.removeItem(integer);
            }
        });
    }

    private void callApi() {
        Call<List<ImageDatum>> call = ApiConfig.getApiCon().create(ApiInterface.class).getAllImages();

        call.enqueue(new Callback<List<ImageDatum>>() {
            @Override
            public void onResponse(Call<List<ImageDatum>> call, Response<List<ImageDatum>> response) {
                if (response.isSuccessful()) {
                    imageAdapter.setList(response.body());
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<ImageDatum>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setRecyclerAdapter() {
        imageAdapter = new ImageAdapter(this);
        recyclerViewImage.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewImage.setHasFixedSize(true);
        recyclerViewImage.setAdapter(imageAdapter);

        tableAdapter = new TableAdapter(this);
        recyclerViewTable.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTable.setHasFixedSize(true);
        recyclerViewTable.setAdapter(tableAdapter);
    }
}