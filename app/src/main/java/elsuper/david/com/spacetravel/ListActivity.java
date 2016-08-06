package elsuper.david.com.spacetravel;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import elsuper.david.com.spacetravel.data.ApodService;
import elsuper.david.com.spacetravel.data.Data;
import elsuper.david.com.spacetravel.model.MarsRovertResponse;
import elsuper.david.com.spacetravel.model.Photo;
import elsuper.david.com.spacetravel.ui.view.apod.list.adapter.NasaApodAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {

    @BindView(R.id.mars_rover_listing) RecyclerView marsRoverListingRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(10,StaggeredGridLayoutManager.VERTICAL);
        //marsRoverListingRecycler.setLayoutManager(LinearLayoutManager);
        marsRoverListingRecycler.setLayoutManager(gridLayoutManager);
        //marsRoverListingRecycler.setLayoutManager(staggeredGridLayoutManager);

        //Para manejar el click en la foto
        final NasaApodAdapter nasaApodAdapter = new NasaApodAdapter();
        nasaApodAdapter.setOnItemClickListener(new NasaApodAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(Photo photo) {
                //Log.d("click", photo.getImgSrc());
                //Toast.makeText(getApplicationContext(),photo.getCamera().getFullName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListActivity.this,DetailActivity.class);
                intent.putExtra("key_fullName", photo.getCamera().getFullName());
                intent.putExtra("key_imgsrc", photo.getImgSrc());
                intent.putExtra("key_earthDate", photo.getEarthDate());
                intent.putExtra("key_cameraName", photo.getCamera().getName());
                startActivity(intent);
            }
        });



        ApodService apodService = Data.getRetrofitInstance().create(ApodService.class);

        apodService.getTodayMarsRovertWithQuery(400, BuildConfig.NASA_API_KEY).enqueue(new Callback<MarsRovertResponse>() {
            @Override
            public void onResponse(Call<MarsRovertResponse> call, Response<MarsRovertResponse> response) {

                nasaApodAdapter.setMarsPhotos(response.body().getPhotos());
                marsRoverListingRecycler.setAdapter(nasaApodAdapter);
                //marsRoverListingRecycler.setAdapter(new NasaApodAdapter(response.body().getPhotos()));
            }

            @Override
            public void onFailure(Call<MarsRovertResponse> call, Throwable t) {

            }
        });

    }
}

