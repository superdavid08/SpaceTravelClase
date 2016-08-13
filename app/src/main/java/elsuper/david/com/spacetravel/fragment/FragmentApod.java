package elsuper.david.com.spacetravel.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import elsuper.david.com.spacetravel.BuildConfig;
import elsuper.david.com.spacetravel.ListActivity;
import elsuper.david.com.spacetravel.R;
import elsuper.david.com.spacetravel.data.ApodService;
import elsuper.david.com.spacetravel.data.Data;
import elsuper.david.com.spacetravel.model.APOD;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alumno on 12/08/2016.
 */
public class FragmentApod extends Fragment {

    @BindView(R.id.apodImg)
    ImageView imageView;//2016-08-05
    @BindView(R.id.tvDate)
    TextView tvDate;//2016-08-05
    @BindView(R.id.tvTitle) TextView tvTitle;//2016-08-05
    @BindView(R.id.tvExplanation) TextView tvExplanation;//2016-08-05
    @BindView(R.id.tvCopyright) TextView tvCopyright;//2016-08-05
    @BindView(R.id.main_btnGoList)
    Button btnGoToList;//2016-08-05

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        //Inflamos la vista
        View view = inflater.inflate(R.layout.fragment_apod,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Log.d("BuildConfig", BuildConfig.URL);

        //Utilizando Retrofit
        ApodService apodService = Data.getRetrofitInstance().create(ApodService.class);

        //Call<APOD> callApodService = apodService.getTodayApod();
        Call<APOD> callApodService = apodService.getTodayApodWithQuery("J0U8OnXkzemf1OF32OotEIYYrdOfWyUsdGKnxjaj");

        callApodService.enqueue(new Callback<APOD>() {
            @Override
            public void onResponse(Call<APOD> call, Response<APOD> response) {
                Log.d("APOD", response.body().getTitle());

                //Asignando valores
                Picasso.with(getActivity()).load(response.body().getHdurl()).into(imageView);
                tvDate.setText(response.body().getDate());
                tvTitle.setText(response.body().getTitle());
                tvExplanation.setText(response.body().getExplanation());
                tvCopyright.setText("Copyright: " + response.body().getCopyright());
            }

            @Override
            public void onFailure(Call<APOD> call, Throwable t) {

            }
        });

        btnGoToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ListActivity.class));
            }
        });
    }

    //2016-08-13
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.apod_menu,menu);
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
                shareText("Diplomado " + tvTitle.getText());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareText(String text){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(shareIntent,"Compartir"));
    }
}
