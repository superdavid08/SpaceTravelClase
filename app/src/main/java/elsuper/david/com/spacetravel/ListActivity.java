package elsuper.david.com.spacetravel;

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

        ApodService apodService = Data.getRetrofitInstance().create(ApodService.class);

        apodService.getTodayMarsRovertWithQuery(400, BuildConfig.NASA_API_KEY).enqueue(new Callback<MarsRovertResponse>() {
            @Override
            public void onResponse(Call<MarsRovertResponse> call, Response<MarsRovertResponse> response) {

                marsRoverListingRecycler.setAdapter(new NasaApodAdapter(response.body().getPhotos()));
            }

            @Override
            public void onFailure(Call<MarsRovertResponse> call, Throwable t) {

            }
        });

    }
}

