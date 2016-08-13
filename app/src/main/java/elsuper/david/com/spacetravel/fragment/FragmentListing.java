package elsuper.david.com.spacetravel.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import elsuper.david.com.spacetravel.BuildConfig;
import elsuper.david.com.spacetravel.DetailActivity;
import elsuper.david.com.spacetravel.R;
import elsuper.david.com.spacetravel.data.ApodService;
import elsuper.david.com.spacetravel.data.Data;
import elsuper.david.com.spacetravel.model.MarsRovertResponse;
import elsuper.david.com.spacetravel.model.Photo;
import elsuper.david.com.spacetravel.ui.view.apod.list.adapter.NasaApodAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alumno on 12/08/2016.
 */
public class FragmentListing extends Fragment{

    @BindView(R.id.mars_rover_listing)
    RecyclerView marsRoverListingRecycler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        //Inflamos la vista
        View view = inflater.inflate(R.layout.fragment_listing,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setSupportActionBar(toolbar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(),2);
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
                Intent intent = new Intent(view.getContext(),DetailActivity.class);
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

    //2016-08-13
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.list_rover_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.share_today_apod:
                shareText("Diplomado lista ");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareText(String text){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(shareIntent,"Compartir "));
    }
}
